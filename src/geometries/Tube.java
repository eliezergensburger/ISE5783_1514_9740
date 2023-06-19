package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends RadialGeometry {

    final protected Ray axisRay; // The axis ray of the tube

    /**
     * Constructor: Initializes a new instance of the Tube class.
     *
     * @param radius  The radius of the tube.
     * @param axisRay The axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the normal vector to the surface of the tube at the given point.
     *
     * @param point The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        //by the formulla in the slides
        double t = axisRay.getDir().dotProduct(
                point.subtract(
                        axisRay.getP0()));
        Point O = axisRay.getP0().add(
                axisRay.getDir().scale(t));
        return point.subtract(O).normalize();
    }

    /**

     Finds the intersection points of the given ray with the geometry.
     @param ray The ray to intersect with the geometry.
     @return A list of {@link GeoPoint} objects representing the intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //taken from the web: https://mrl.cs.nyu.edu/~dzorin/rend05/lecture2.pdf

        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Point pa = this.axisRay.getP0();
        Vector va = this.axisRay.getDir();

        double a, b, c; //coefficients for quadratic equation ax^2 + bx + c

        // a = (v-(v,va)va)^2
        // b = 2(v-(v,va)va,delP-(delP,va)va)
        // c = (delP-(delP,va)va)^2 - r^2
        // delP = p0-pa

        //note: (v,u) = v dot product u = vu = v*u

        //Step 1 - calculates a:
        Vector vecA = v;
        try {
            double vva = v.dotProduct(va); //(v,va)
            if (!isZero(vva)) vecA = v.subtract(va.scale(vva)); //v-(v,va)va
            a = vecA.lengthSquared(); //(v-(v,va)va)^2
        } catch (IllegalArgumentException e) {
            return null; //if a=0 there are no intersections because Ray is parallel to axisRay
        }

        //Step 2 - calculates deltaP (delP), b, c:
        try {
            Vector deltaP = p0.subtract(pa); //p0-pa
            Vector deltaPMinusDeltaPVaVa = deltaP;
            double deltaPVa = deltaP.dotProduct(va); //(delP,va)va)
            if (!isZero(deltaPVa)) deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); //(delP-(delP,va)va)
            b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); //2(v-(v,va)va,delP-(delP,va)va)
            c = deltaPMinusDeltaPVaVa.lengthSquared() - this.radius*this.radius; //(delP-(delP,va)va)^2 - r^2
        } catch (IllegalArgumentException e) {
            b = 0; //if delP = 0, or (delP-(delP,va)va = (0, 0, 0)
            c = -1 * this.radius*this.radius;
        }

        //Step 3 - solving the quadratic equation: ax^2 + bx + c = 0
        double discriminator = alignZero(b * b - 4 * a * c); //discriminator: b^2 - 4ac
        if (discriminator <= 0) return null; //there are no intersections because Ray is parallel to axisRay

        //the solutions for the equation: (-b +- discriminator) / 2a
        double sqrtDiscriminator = Math.sqrt(discriminator);
        double t1 = alignZero(-b + sqrtDiscriminator) / (2 * a);
        double t2 = alignZero(-b - sqrtDiscriminator) / (2 * a);

        //if t1 or t2 are bigger than maxDistance they wll be set to negative value
        if (alignZero(t1 - maxDistance) > 0) t1 = -1;
        if (alignZero(t2 - maxDistance) > 0) t2 = -1;

        //takes all positive solutions
        if (t1 > 0 && t2 > 0)
            return twoPoints(ray, new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0) return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return null; //if there are no positive solutions
    }

    /**
     * Create list of two points on a ray according to the order from ray's head.
     *
     * @param ray to check the distance from its head.
     * @param p1  1st point.
     * @param p2  2nd point.
     * @return ordered list of the two points.
     */
    protected List<GeoPoint> twoPoints(Ray ray, GeoPoint p1, GeoPoint p2) {
        Point p0 = ray.getP0();
        return p0.distanceSquared(p1.point) <= p0.distanceSquared(p2.point) ? List.of(p1, p2) : List.of(p2, p1);
    }


}