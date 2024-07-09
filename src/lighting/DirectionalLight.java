package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a new light source with the given intensity
     *
     * @param iD        color intensity
     * @param direction
     */
    public DirectionalLight(Color iD, Vector direction) {
        super(iD);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point point) {
        return intensity;
    }

    public Vector getL(Point point) {
        return direction;
    }

    @Override
    public double getDistance(Point nwePoint) {
        return Double.POSITIVE_INFINITY;
    }


}
