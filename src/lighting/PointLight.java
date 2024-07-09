package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private Point position;
    private double kC = 1d;
    private double kL = 0d;
    private double kQ = 0d;

    /**
     * getter for the intensity of the light source
     *
     * @param position the point at which the intensity is calculated
     * @return the intensity of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

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

    public Color getIntensity(Point point) {
        double d = position.distance(point);
        return intensity.scale(1d / (kC + kL * d + kQ * d * d));
    }

    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }


}
