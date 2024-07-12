package lighting;


import primitives.Color;

/**
 * Represents an ambient light source
 */
public class AmbientLight extends Light {

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0d);

    /**
     * Constructs a new ambient light source with the given intensity
     *
     * @param iA the intensity of the light source
     */

    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}
