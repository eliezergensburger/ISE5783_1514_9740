package lighting;

import primitives.Color;

/**
 * Light is an abstract class.
 *
 * @author Israel Jacob & Avraham Meiri
 */
abstract class Light {
    private Color intensity;

    /**
     * Constructs a light source with the specified intensity.
     *
     * @param intensity The intensity of the light source, represented by a {@link Color} object.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * /**
     * Getter for the light intensity.
     *
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}