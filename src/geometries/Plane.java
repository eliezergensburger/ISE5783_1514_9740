package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {

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
        Vector v1 = p1.subtract(p2) ; // two new vectors
        Vector v2 =p1.subtract(p3) ; // two new vectors ;
        normal = v1.crossProduct(v2).normalize();
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
        return normal;
    }

    /**
     * Returns the normal vector to the surface of the plane.
     *
     * @return A Vector object representing the normal vector to the surface of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * @param ray
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // ray begins at q0 of the plane
        if (p0.equals(P0)) {
            return null;
        }

        // ray is laying in the plane axis
        double nv = n.dotProduct(v);

        //ray direction cannot be parallel to plane orientation
        if (isZero(nv)) {
            return null;
        }

        Vector P0_Q0 = p0.subtract(P0);

        // numerator
        double nQMinusP0 = alignZero(n.dotProduct(P0_Q0));

        // t should be > 0
        if (isZero(nQMinusP0)) {
            return null;
        }

        double t = alignZero(nQMinusP0 / nv);

        // t should be > 0
        if (t <= 0) {
            return null;
        }

        // return immutable List
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    /**
     * Finds the intersection points of the ray with the surface of the object
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @param maxDistance The maximum distance from the source of the ray to intersect with.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // ray begins at q0 of the plane
        if (p0.equals(P0)) {
            return null;
        }

        // ray is laying in the plane axis
        double nv = n.dotProduct(v);

        //ray direction cannot be parallel to plane orientation
        if (isZero(nv)) {
            return null;
        }

        Vector P0_Q0 = p0.subtract(P0);

        // numerator
        double nQMinusP0 = alignZero(n.dotProduct(P0_Q0));

        // t should be > 0
        if (isZero(nQMinusP0)) {
            return null;
        }

        double t = alignZero(nQMinusP0 / nv);

        // t should be > 0
        if (t < 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }

        // return immutable List
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}