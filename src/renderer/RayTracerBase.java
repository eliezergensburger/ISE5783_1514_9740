package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * This class used to trace rays for the rendering engine
 *
 * @author Israel Jacob & Avraham Meiri
 */
public abstract class RayTracerBase {
    protected final Scene scene;

    /**
     * Constructor for RayTracerBase
     *
     * @param scene אhe scene to be rendered.
     */
    protected RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Given a ray, trace it through the scene and return the color of the pixel that it hits.
     *
     * @param ray The ray to trace.
     * @return A color object.
     */
    abstract Color traceRay(Ray ray);
}