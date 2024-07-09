package lighting;

import geometries.Intersectable;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC=1d;
    private double kL= 0d;
    private double kQ= 0d;

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * getter for the intensity of the light source
     * @param point the point at which the intensity is calculated
     * @return the intensity of the light source
     */
    public PointLight(Color intensity, Point position){
        super(intensity);
        this.position = position;
    }

    public Color getIntensity(Point point){
        double d = position.distance(point);
        return intensity.scale(1/(kC + kL*d+ kQ*(d*d)));
    }

    public Vector getL(Point point){
        return position.subtract(point).normalize();
    }

    @Override
    public double getDistance(Point nwePoint) {
        return position.distance(nwePoint);
    }


}
