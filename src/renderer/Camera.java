package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.Random;

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

    public List<Ray> constructRectangleOfRays(int nX, int nY, int j, int i, int size) {
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

        List<Ray> rays = new ArrayList<>(size);
        Random rand = new Random();

        for (int k = 0; k < size; k++) {
            // Generate random point within the pixel area
            double offsetX = (rand.nextDouble() - 0.5) * rX; // random value between -rX/2 and rX/2
            double offsetY = (rand.nextDouble() - 0.5) * rY; // random value between -rY/2 and rY/2

            Point randomPoint = Pij.add(vRight.scale(offsetX)).add(vUp.scale(offsetY));
            Vector rayDirection = randomPoint.subtract(p0);
            rays.add(new Ray(p0, rayDirection));
        }

        return rays;
    }





    /**
     * Renders the image.
     */
    public Camera renderImage(int size) {
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                castRay(imageWriter.getNx(), imageWriter.getNy(), j, i,size);
            }
        }
        return this;
    }

    /**
     * Casts a ray from the camera through a pixel in the view plane.
     *
     * @param nX The number of pixels in the x-axis
     * @param nY The number of pixels in the y-axis
     * @param j  The x-coordinate of the pixel
     * @param i  The y-coordinate of the pixel
     */
    private void castRay(int nX, int nY, int j, int i, int size) {
        if(size == 1){
            Ray ray = constructRay(nX, nY, j, i);
            imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
        }
        else{
            List<Ray> rays = constructRectangleOfRays(nX, nY, j, i, size);
            Color color = Color.BLACK;
            for(Ray ray : rays){
                color = color.add(rayTracer.traceRay(ray));
            }
            imageWriter.writePixel(j, i, color.reduce(rays.size()));
        }
    }


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

                if (camera.vUp.dotProduct(camera.vTo) != 0)
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

