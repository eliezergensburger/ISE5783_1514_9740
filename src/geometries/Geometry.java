package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the normal vector to the surface of the geometry at the given point.
     *
     * @param p The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    public abstract Vector getNormal(Point p);
}
