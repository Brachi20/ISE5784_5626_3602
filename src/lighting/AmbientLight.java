package lighting;


import primitives.Color;
import primitives.Double3;

/**
 * Represents an ambient light source
 */
public class AmbientLight extends Light {

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs a new ambient light source with the given intensity
     *
     * @param iA the intensity of the light source
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }


}
