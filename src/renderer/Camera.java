package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class Camera implements Cloneable {


    private Point p0 = Point.ZERO;
    private Vector vUp = null;
    private Vector vTo = null;
    private Vector vRight = null;
    private double width = 0d;
    private double height = 0d;
    private double distance = 0d;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int antialiasingLevel = 1;
    private PixelManager pixelManager;
    private int threadsCount;
    private boolean adaptive = false;

    private Camera() {
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

/**
     * Constructs a ray that passes through a pixel in the view plane.
     * @param nX The number of pixels in the width.
     * @param nY The number of pixels in the height.
     * @param j The column index of the pixel.
     * @param i The row index of the pixel.
     * @return The ray that passes through the pixel.
 */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double rX = width / nX;
        double rY = height / nY;

        double Xj = (j - (nX - 1) / 2d) * rX;
        double Yi = -(i - (nY - 1) / 2d) * rY;

        Point pCenter = p0.add(vTo.scale(distance));

        Point Pij = pCenter;
        if (!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi))
            Pij = Pij.add(vUp.scale(Yi));

        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }


    /**
     * Constructs a list of rays that form a rectangle in the view plane.
     * @param nX The number of pixels in the width.
     * @param nY The number of pixels in the height.
     * @param j The column index of the pixel.
     * @param i The row index of the pixel.
     * @param numRays The number of rays to construct.
     * @return The list of rays that form the rectangle.
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i, int numRays) {
        double rX = width / nX;
        double rY = height / nY;

        double Xj = (j - (nX - 1) / 2d) * rX;
        double Yi = -(i - (nY - 1) / 2d) * rY;

        Point pCenter = p0.add(vTo.scale(distance));

        Point Pij = pCenter;
        if (!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi))
            Pij = Pij.add(vUp.scale(Yi));

        List<Ray> rays = new ArrayList<>(numRays);
        Random rand = new Random();

        for (int k = 0; k < numRays; k++) {
            // Generate random point within the pixel area
            double offsetX = (rand.nextDouble() - 0.5) * rX; // random value between -rX/2 and rX/2
            double offsetY = (rand.nextDouble() - 0.5) * rY; // random value between -rY/2 and rY/2

            Point randomPoint = Pij.add(vRight.scale(offsetX)).add(vUp.scale(offsetY));
            Vector rayDirection = randomPoint.subtract(p0);
            rays.add(new Ray(p0, rayDirection));
        }

        return rays;
    }


     /* Renders the image by casting rays through each pixel.
     * @return The camera after rendering the image.
     */
    public Camera renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        // Verify that nX and nY are not zero to avoid division by zero
        if (nY == 0 || nX == 0)
            throw new IllegalArgumentException("It is impossible to divide by 0");
        // Initialize the pixel manager
        pixelManager = new PixelManager(nY, nX, 0.1);
        // Check if the number of threads is 0
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i, antialiasingLevel);
        }
        else { // see further... option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row(),antialiasingLevel);
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return this;
    }






        /**
     * Casts a num of rays through a pixel and writes the resulting color to the image.
     * @param nX Number of pixels in width.
     * @param nY Number of pixels in height.
     * @param column The column index of the pixel.
     * @param row The row index of the pixel.
     */
    private void castRay(int nX, int nY, int column, int row, int numRays) {
        Color color = Color.BLACK;
        if (numRays == 1) {
            // Trace a single ray
            Ray ray = constructRay(nX, nY, column, row);
            color = rayTracer.traceRay(ray);
        } else {
            boolean colorsDifferernt = false;
            if(adaptive){
            // Trace multiple rays
            List<Ray> rays = constructRays(nX, nY, column, row, 5);
            if (!rays.isEmpty()) {
                // Handle empty rays list, if applicable
                Color firstColor = rayTracer.traceRay(rays.get(0));
                // Check if all rays produce the same color
                for (Ray ray : rays) {
                    Color currentColor = rayTracer.traceRay(ray);
                    if (!currentColor.equals(firstColor)) {
                        colorsDifferernt = true;
                        break;
                    }
                }
                if (colorsDifferernt) {
                    // If the colors are different, construct more rays
                     rays = constructRays(nX, nY, column, row, numRays);
                     color = AvrageColor(rays, color);
                } else {
                    color = firstColor;
                }
            }
            } else{
                List<Ray> rays = constructRays(nX, nY, column, row, numRays);
                color= AvrageColor(rays, color);

        }}

        // Write the computed color to the image and mark the pixel as done
        imageWriter.writePixel(column, row, color);
        pixelManager.pixelDone();
    }



        /**
     * Calculates the average color from a list of rays.
     * @param rays The list of rays to calculate the average color from.
     * @param color The initial color to add to.
     * @return The average color from the list of rays.
     */
    private Color AvrageColor(List<Ray> rays, Color color) {
        if(rays.isEmpty())
            return Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(rayTracer.traceRay(ray));
        }
        color = color.reduce(rays.size());
        return color;
    }

    /**
     * Prints a grid on the image.
     *
     * @param interval The interval between grid lines
     * @param color    The color of the grid lines
     * @return The camera after printing the grid
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;

    }

    /**
     * Writes the image to a file.
     */
    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }

    /**
     * Orients the camera to look at a specific point in the scene with a given up vector
     *
     * @param point    the point to look at
     * @param upVector the general up vector (default is usually (0, 1, 0))
     * @return this object for chaining
     */
    public Camera lookAt(Point point, Vector upVector) {
        // Calculate direction vector
        Vector direction = point.subtract(p0).normalize();

        // Normalize the up vector
        upVector = upVector.normalize();

        // Calculate right vector
        Vector right = direction.crossProduct(upVector).normalize();

        // Calculate the new up vector
        Vector newUp = right.crossProduct(direction).normalize();

        // Update the camera vectors
        this.vTo = direction;
        this.vRight = right;
        this.vUp = newUp;

        return this;
    }


    /**
     * Represents a builder for the camera class.
     */
    public static class Builder {

        private static final String MISSING_RESOURCES = "ERROR:missing render resource ";
        private static final String NAME_OF_CLASS = "Camera ";

        private final Camera camera = new Camera();

        // Intermediate data fields
        private Point lookAtPoint;
        private Vector upVector = null;

        /**
         * Sets the camera's position.
         *
         * @param p The position
         * @return The builder
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * Sets the camera's up vector.
         *
         * @param up The up vector
         * @param to The to vector
         * @return The builder
         * @throws IllegalArgumentException if the up and to vectors are not orthogonal
         */
        public Builder setDirection(Vector to, Vector up) {
            if (!isZero(to.dotProduct(up)))
                throw new IllegalArgumentException("ERROR:up and to are not orthogonal");
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            return this;
        }
        public Builder setDirection(Point front, Vector up) {
            camera.vUp = up.normalize();

            if(camera.p0.equals(front)){
                throw new IllegalArgumentException("ERROR:front cannot be the same as the camera location");
            }
            Vector towards = front.subtract(camera.p0).normalize();
            if(towards.dotProduct(camera.vUp) == 0){
                throw new IllegalArgumentException("ERROR:front and up are not orthogonal");
            }
            camera.vTo = towards;
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return  this;
        }
        /**
         * Sets the camera's distance from the view plane.
         *
         * @param width  The width of the view plane
         * @param height The height of the view plane
         * @return The builder
         */
        public Builder setVpSize(double width, double height) {
            camera.width = alignZero(width);
            camera.height = alignZero(height);
            return this;
        }

        /**
         * Sets the camera's distance from the view plane.
         *
         * @param distance The distance
         * @return The builder
         */
        public Builder setVpDistance(double distance) {
            camera.distance = alignZero(distance);
            return this;
        }

        /**
         * Sets the camera's ray tracer.
         *
         * @param rayTracer The ray tracer
         * @return The builder
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the camera's image writer.
         *
         * @param imageWriter The image writer
         * @return The builder
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the camera's number of threads.
         *
         * @param threadsCount The number of threads
         * @return The builder
         */
        public Builder setThreadsCount(int threadsCount) {
            camera.threadsCount = threadsCount;
            return this;
        }

        /**
         * Sets the camera's antialiasing level.
         *
         * @param antialiasingLevel The antialiasing level
         * @return The builder
         */
        public Builder setAntialiasingLevel(int antialiasingLevel) {
            camera.antialiasingLevel = antialiasingLevel;
            return this;
        }

        /**
         * Sets the camera's adaptive flag.
         *
         * @param adaptive The adaptive flag
         * @return The builder
         */
        public Builder setAdaptive(boolean adaptive) {
            camera.adaptive = adaptive;
            return this;
        }


        /**
         * Sets the camera's look at point.
         *
         * @param lookAtPoint The look at point
         * @return The builder
         */
        public Builder setLookAtPoint(Point lookAtPoint) {
            this.lookAtPoint = lookAtPoint;
            return this;
        }

        /**
         * Sets the camera's up vector.
         *
         * @param upVector The up vector
         * @return The builder
         */
        public Builder setUpVector(Vector upVector) {
            this.upVector = upVector;
            return this;
        }

        /**
         * Builds the camera.
         *
         * @return The camera
         */
        public Camera build() {
            if (camera.width == 0)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "width");

            if (camera.height == 0)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "height");

            if (isZero(camera.distance))
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "distance");

            if (camera.distance < 0)
                throw new IllegalArgumentException("ERROR:distance must be positive");

            if (camera.p0 == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "location");

            if (lookAtPoint != null && upVector != null) {
                // Update the camera vectors based on lookAtPoint and upVector
                camera.lookAt(lookAtPoint, upVector);
            } else {
                if (camera.vUp == null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "up vector");

                if (camera.vTo == null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "to vector");

                if (!isZero(camera.vUp.dotProduct(camera.vTo)))
                    throw new IllegalArgumentException("ERROR:up and to are not orthogonal");

                camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

                if (camera.vRight == null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "right vector");
            }

//            if (camera.imageWriter == null)
//                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "image writer");

//            if (camera.rayTracer == null)
//                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "ray tracer");


            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                return null;
            }
        }


    }
}

