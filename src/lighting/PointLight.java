package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A point light is a light source that has a position in space and a color
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class PointLight extends Light implements LightSource{

    private final Point position;

    private double kC = 1d; // constant attenuation factor
    private double kL = 0d; // light's attenuation factor
    private double kQ = 0d; // quadratic attenuation factor

    /**
     * Constructor for PointLight class
     *
     * @param intensity The intensity of the PointLight
     */
    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Setter for the Kc field.
     *
     * @param kC Constant attenuation factor
     * @return The object itself.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for the Kl field.
     *
     * @param kL Attenuation factor for the light source.
     * @return The object itself.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for the Kq field.
     *
     * @param kQ The attenuation factor.
     * @return The object itself.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Implementation of getIntensity from LightSource interface.
     *
     * @param point The origin of the light.
     * @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        // Calculating the distance between the point
        // and the position of the light source.
        double distance = point.distance(position);
        double distanceSquared = point.distanceSquared(position);

        // Calculating the intensity of the light at a given point.
        double factor = (kC + kL *distance + kQ *distanceSquared);
        Color Ic = getIntensity();

        // Reducing the intensity of the light by the factor.
        return Ic.reduce(factor);
    }

    /**
     * Implementation og getL from LightSource interface.
     *
     * @param point Starting point.
     * @return The direction of the light
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return this.position.distance(point);
    }
}