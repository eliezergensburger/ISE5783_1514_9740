package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * A directional light is a light that has a direction and an intensity
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * Constructor for DirectionalLight class
     *
     * @param intensity The intensity of the color
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Implementation of getIntensity from LightSource interface.
     *
     * @param point The origin of the light.
     * @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    /**
     * Implementation og getL from LightSource interface.
     *
     * @param point Starting point.
     * @return The direction of the light
     */
    @Override
    public Vector getL(Point point) {
        return this.direction;
    }
}