package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * This class used to trace rays for the rendering engine
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor for RayTracerBasic
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Given a ray, trace it through the scene and return the color of the pixel that it hits.
     *
     * @param ray The ray to trace.
     * @return A color object.
     */
    @Override
    Color traceRay(Ray ray) {
        // Get all the intersections
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        // If there is intersections
        if (intersections != null) {
            // Calculates the closest point to the intersection and returns its color
            Point closestPoint = ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        // If there is no intersections at all
        else
            return this.scene.background;
    }

    /**
     * Given a point, calculates and returns the color of this point
     *
     * @param point The point on the surface of the object that we're shading.
     * @return The ambient light intensity.
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
