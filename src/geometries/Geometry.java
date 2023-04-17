package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {

    /**
     * Returns the normal vector to the surface of the geometry at the given point.
     *
     * @param p The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    public Vector getNormal(Point p);
}
