package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.lang.constant.Constable;
import java.util.MissingResourceException;

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


    private Camera() {
    }

    public static Builder getBuilder() {
        return new Builder();
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

    /**
     * Renders the image.
     */
    public Camera renderImage() {
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
            }
        }
        return this;
    }

    /**
     * Casts a ray.
     *
     * @param nX The x coordinate
     * @param nY The y coordinate
     * @param i  The i coordinate
     * @param j  The j coordinate
     */
    private void castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
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
     * @param point the point to look at
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
        private Vector upVector=null;

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

