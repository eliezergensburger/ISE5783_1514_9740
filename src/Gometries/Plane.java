package Geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final private Point p0; // A point on the plane
    final  private Vector normal; // The normal vector to the plane

    /**
     * Constructor: Initializes a new instance of the Plane class.
     *
     * @param p1 A Point object representing a point on the plane.
     * @param p2 A Point object representing another point on the plane.
     * @param p3 A Point object representing a third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3)
    {
        p0 = p1;
        normal = null;
    }

    /**
     * Constructor: Initializes a new instance of the Plane class.
     *
     * @param p0 A Point object representing a point on the plane.
     * @param normal A Vector object representing the normal vector to the plane.
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    /**
     * Returns the normal vector to the surface of the plane at the given point.
     *
     * @param p The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    /**
     * Returns the normal vector to the surface of the plane.
     *
     * @return A Vector object representing the normal vector to the surface of the plane.
     */
    public Vector getNormal() {
        return null;
    }
}
