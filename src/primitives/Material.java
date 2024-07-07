package primitives;

public class Material {

    public Double3 kD=Double3.ZERO,kS=Double3.ZERO;
    public int nShininess=0;

    /**
     * setter for the diffuse reflection coefficient
     * @param kD of Double3 type
     * @return the material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for the diffuse reflection coefficient
     * @param kD of double type
     * @return the material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setter for the specular reflection coefficient
     * @param kS of Double3 type
     * @return the material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter for the specular reflection coefficient
     * @param kS of double type
     * @return the material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter for the shininess coefficient
     * @param nShininess of int type
     * @return the material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
