package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.lang.constant.Constable;
import java.util.MissingResourceException;

public class Camera implements Cloneable{


    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width=0d;
    private double height=0d;
    private double distance=0d;

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


    private Camera(){
        p0 = new Point(0,0,0);
        vUp = new Vector(0,1,0);
        vTo = new Vector(0,0,-1);
        vRight = new Vector(1,0,0);
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        double Rx = width/nX;
        double Ry = height/nY;
        double Xj = (j - (nX-1)/2d)*Rx;
        double Yi = -(i - (nY-1)/2d)*Ry;
        Point Pij = p0.add(vTo.scale(distance));
        if(Xj!=0)
            Pij = Pij.add(vRight.scale(Xj));
        if(Yi!=0)
            Pij = Pij.add(vUp.scale(-Yi));
        Vector Vij = Pij.subtract(p0);
        return new Ray(p0,Vij);
    }



    /**
     * Represents a builder for the camera class.
     */
    public static class Builder{

        private static final String MISSING_RESOURCES="ERROR:missing render resource ";
        private static final String NAME_OF_CLASS="Camera ";

        private final Camera camera;

        /**
         * Constructs a new builder.
         */
        public Builder(){
            camera=new Camera();
        }

        /**
         * Constructs a new builder with the given camera.
         * @param newCamera The camera to use
         */
        public Builder(Camera newCamera){
            camera=newCamera;
        }

        /**
         * Sets the camera's position.
         * @param p The position
         * @return The builder
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * Sets the camera's up vector.
          * @param up The up vector
         * @param to The to vector
         * @return The builder
         * @throws IllegalArgumentException if the up and to vectors are not orthogonal
         */
       public Builder setDirection(Vector up, Vector to){
           if(up.dotProduct(to)!=0)
               throw new IllegalArgumentException("ERROR:up and to are not orthogonal");
           camera.vUp = up.normalize();
           camera.vTo = to.normalize();
           return this;
       }

        /**
         * Sets the camera's distance from the view plane.
         * @param width The width of the view plane
         * @param height The height of the view plane
         * @return The builder
         */
       public Builder setVpSize(double width, double height){
           camera.width = width;
           camera.height = height;
           return this;
       }

        /**
         * Sets the camera's distance from the view plane.
         * @param distance The distance
         * @return The builder
         */
       public Builder setVpDistance(double distance){
           camera.distance = distance;
           return this;
       }

         /**
          * Builds the camera.
          * @return The camera
          */
         public Camera build(){


             if(camera.width==0)
                 throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "width");

             if(camera.height==0)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "height");

             if(camera.distance==0)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "distance");

             if(camera.p0==null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "location");

             if(camera.vUp==null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "up vector");

             if(camera.vTo==null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "to vector");

                if(camera.vRight==null)
                    throw new MissingResourceException(MISSING_RESOURCES, NAME_OF_CLASS, "right vector");

             if(camera.vUp.dotProduct(camera.vTo)!=0)
                 throw new IllegalArgumentException("ERROR:up and to are not orthogonal");

             camera.vTo=camera.vTo.normalize();
             camera.vUp=camera.vUp.normalize();
             camera.vRight= camera.vTo.crossProduct(camera.vUp).normalize();

             if(camera.distance<0)
                 throw new IllegalArgumentException("ERROR:distance must be positive");

             try {
                 return (Camera) camera.clone();
             } catch (CloneNotSupportedException e) {
                 throw new RuntimeException(e);
             }
         }

    }










}
