package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient Light for all objects in 3D space
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class AmbientLight extends Light {

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Constructor
     *
     * @param Ia Light illumination
     * @param Ka Light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

}