package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source
 */
public interface LightSource {

    /**
     * getter for the intensity of the light source
     * @param point the point at which the intensity is calculated
     * @return the intensity of the light source
     */
    public Color getIntensity(Point point);
    /**
     * getter for the vector from the light source to the point
     * @param point the point at which the vector is calculated
     * @return the vector from the light source to the point
     */
    public Vector getL(Point point);
}
