package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**

 The LightSource interface represents a source of light in a lighting system.
 It provides methods to retrieve the intensity of the light at a specific point
 and the direction of the light from that point.
 */
public interface LightSource {
    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point at which to calculate the light intensity
     * @return the intensity of the light as a Color object
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light from the given point.
     *
     * @param p the point from which to calculate the light direction
     * @return the direction of the light as a Vector object
     */
    public Vector getL(Point p);
}
