package primitives;

public class Material {

    /**
     * The material's coefficients
     */
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO, kT = Double3.ZERO, kR = Double3.ZERO;
    public int nShininess = 0;

    /**
     * setter for the diffuse reflection coefficient
     *
     * @param kD of Double3 type
     * @return the material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        if(Double3.ONE.lowerThan(kD.add(kS.add(kT))))
            this.kD=Double3.ONE.subtract(kS.add(kT));
        return this;
    }

    /**
     * setter for the diffuse reflection coefficient
     *
     * @param kD of double type
     * @return the material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        if(Double3.ONE.lowerThan(this.kD.add(kS.add(kT))))
            this.kD=Double3.ONE.subtract(kS.add(kT));
        return this;
    }

    /**
     * setter for the specular reflection coefficient
     *
     * @param kS of Double3 type
     * @return the material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        if(Double3.ONE.lowerThan(kD.add(kS.add(kT))))
            this.kS=Double3.ONE.subtract(kD.add(kT));
        return this;
    }

    /**
     * setter for the specular reflection coefficient
     *
     * @param kS of double type
     * @return the material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        if(Double3.ONE.lowerThan(kD.add(this.kS.add(kT))))
            this.kS=Double3.ONE.subtract(kD.add(kT));
        return this;
    }

    /**
     * setter for the transparency coefficient
     *
     * @param kT of Double3 type
     * @return the material
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        if(Double3.ONE.lowerThan(kD.add(kS.add(kT))))
            this.kT=Double3.ONE.subtract(kD.add(kS));
        return this;
    }

    /**
     * setter for the transparency coefficient
     *
     * @param kT of double type
     * @return the material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        if(Double3.ONE.lowerThan(kD.add(this.kT.add(kS))))
            this.kT=Double3.ONE.subtract(kD.add(kS));
        return this;
    }

    /**
     * setter for the reflection coefficient
     *
     * @param kR of Double3 type
     * @return the material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        if(Double3.ONE.lowerThan(kD.add(kR.add(kT))))
            this.kR=Double3.ONE.subtract(kD.add(kT));
        return this;
    }

    /**
     * setter for the reflection coefficient
     *
     * @param kR of double type
     * @return the material
     */

    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        if(Double3.ONE.lowerThan(kD.add(this.kR.add(kT))))
            this.kR=Double3.ONE.subtract(kD.add(kT));
        return this;
    }



    /**
     * setter for the shininess coefficient
     *
     * @param nShininess of int type
     * @return the material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
