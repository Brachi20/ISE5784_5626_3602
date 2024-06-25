package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.lang.constant.Constable;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;

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
        double Rx = width / nX;
        double Ry = height / nY;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        Point Pij = p0.add(vTo.scale(distance));
        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(-Yi));
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
        for (int i = 0; i < interval; i++) {
            for (int j = 0; j < interval; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
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
     * Represents a builder for the camera class.
     */
    public static class Builder {

        private static final String MISSING_RESOURCES = "ERROR:missing render resource ";
        private static final String NAME_OF_CLASS = "Camera ";

        private final Camera camera = new Camera();


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
        public Builder setDirection(Vector up, Vector to) {
            if (up.dotProduct(to) != 0)
                throw new IllegalArgumentException("ERROR:up and to are not orthogonal");
            camera.vUp = up.normalize();
            camera.vTo = to.normalize();
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
         * Builds the camera.
         *
         * @return The camera
         */
        public Camera build() {


            if (camera.width == 0)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "width");

            if (camera.height == 0)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "height");

            if (camera.distance == 0)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "distance");

            if (camera.p0 == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "location");

            if (camera.vUp == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "up vector");

            if (camera.vTo == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "to vector");

            if (camera.vRight == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "right vector");

            if (camera.vUp.dotProduct(camera.vTo) != 0)
                throw new IllegalArgumentException("ERROR:up and to are not orthogonal");

            if (camera.imageWriter == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "image writer");

            if (camera.rayTracer == null)
                throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "ray tracer");

            if (camera.distance < 0)
                throw new IllegalArgumentException("ERROR:distance must be positive");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();


            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                return null;
            }
        }
    }
}

