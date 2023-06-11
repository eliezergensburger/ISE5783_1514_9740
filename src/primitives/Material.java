package primitives;

/**
 * Material class, the material of the geometries
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class Material {
    public Double3 kD = Double3.ZERO; // diffuse
    public  Double3 kS = Double3.ZERO; // specular

    public  Double3 kT = Double3.ZERO; // Transparency

    public  Double3 kR = Double3.ZERO; // Reflection

    public int nShininess = 0; // shininess

    /**
     * Setter for the Kd field.
     *
     * @param Kd Diffuse reflectivity.
     * @return The material itself.
     */
    public Material setKd(double Kd) {
        this.kD = new Double3(Kd);
        return this;
    }

    /**
     * Setter for the Kd field.
     *
     * @param Kd Diffuse reflectivity.
     * @return The material itself.
     */
    public Material setKd(Double3 Kd) {
        this.kD = Kd;
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
     * Setter for the transparency coefficient field
     *
     * @param Kt The transparency coefficient of the material.
     * @return The material itself.
     */
    public Material setKt(Double3 Kt) {
        this.kT = Kt;
        return this;
    }

    /**
     * Setter for the transparency coefficient field
     *
     * @param Kt The transparency coefficient of the material.
     * @return The material itself.
     */
    public Material setKt(double Kt) {
        this.kT = new Double3(Kt);
        return this;
    }

    /**
     * Setter for the reflection coefficient field.
     *
         * @param Kr The reflection coefficient of the material.
     * @return The material itself.
     */
    public Material setKr(Double3 Kr) {
        this.kR = Kr;
        return this;
    }

    /**
     * Setter for the reflection coefficient field.
     *
     * @param Kr The reflection coefficient of the material.
     * @return The material itself.
     */
    public Material setKr(double Kr) {
        this.kR = new Double3(Kr);
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
