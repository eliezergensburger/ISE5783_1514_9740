package primitives;

/**
 * Material class, the material of the geometries
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class Material {
    public Double3 kD = Double3.ZERO; // diffuse
    public  Double3 kS = Double3.ZERO; // specular
    public int nShininess = 0; // shininess

    /**
     * Setter of the transparency coefficient
     *
     * @param kd coefficient transparency
     * @return the material
     */
    public Material setKt(Double3 kd) {
        this.kD = kd;
        return this;
    }

    /**
     * Setter of the transparency coefficient
     *
     * @param kd transparency coefficient
     * @return the material
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return this;
    }
    /**
     * Setter for the Ks field.
     *
     * @param Ks specular reflectivity
     * @return The material itself.
     */
    public Material setKs(double Ks) {
        this.kS = new Double3(Ks);
        return this;
    }

    /**
     * Setter for the Ks field.
     *
     * @param Ks specular reflectivity
     * @return The material itself.
     */
    public Material setKs(Double3 Ks) {
        this.kS = Ks;
        return this;
    }

    /**
     * Setter for the nShininess field.
     *
     * @param nShininess The shininess of the material.
     * @return The material object itself.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
