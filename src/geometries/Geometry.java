package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;


/**
 * The Geometry class is an abstract class representing a geometric object in a 3D space.
 * It extends the Intersectable class and provides methods for setting and retrieving
 * properties such as emission and material, as well as calculating the normal vector
 * to the surface of the geometry at a given point.
 *
 * @author Israel Jacob & Avraham Meiri
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    private Material material = new Material();

    /**
     * Returns the emission color of the geometry.
     *
     * @return The emission color as a Color object.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to be set.
     * @return The updated Geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the material of the geometry.
     *
     * @return The Material object representing the material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry.
     *
     * @param material The Material object to be set.
     * @return The updated Geometry object.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Calculates and returns the normal vector to the surface of the geometry at the given point.
     *
     * @param p The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    public abstract Vector getNormal(Point p);
}
