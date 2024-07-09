package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a spot light
 */
public class SpotLight extends PointLight {
    private Vector direction;

    /**
     * Constructs a new spot light source with the given intensity, position, and direction
     *
     * @param intensity the intensity of the light source
     * @param position  the position of the light source
     * @param direction the direction of the light source
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    public Vector getL(Point point) {
        return super.getL(point);
    }

    public Color getIntensity(Point point) {
        Color c = super.getIntensity(point);
        Vector l = getL(point);
        double cosTeta = direction.dotProduct(l);
        if (cosTeta <= 0)
            return Color.BLACK;
        else
            return c.scale(cosTeta);

    }


}
