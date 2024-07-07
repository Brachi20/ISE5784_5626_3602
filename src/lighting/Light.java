package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source
 */
abstract class Light {
    protected Color intensity;//Expresses the intensity of the light source

    /**
     * Constructs a new light source with the given intensity
     * @param intensity the intensity of the light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for the intensity of the light source
     * @return the intensity of the light source
     */
    public Color getIntensity() {
        return intensity;
    }





}
