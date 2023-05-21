package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry{

    final private Point center; // The center point of the sphere

    /**
     * Constructor: Initializes a new instance of the Sphere class.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the surface of the sphere at the given point.
     *
     * @param point The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if(P0.equals(center)){
            return List.of(new GeoPoint(this, center.add(v.scale(radius))));
            //throw new IllegalArgumentException("p of Ray is the center of the sphere");
        }

        Vector u = center.subtract(P0);

        double tm = alignZero(u.dotProduct(v));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm) ));

        // no intersections : the ray direction is above the sphere
        if(d >= radius){
            return null;
        }

        double th = alignZero(Math.sqrt( (radius * radius) - (d * d) ));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if(t1 > 0 && t2 > 0){
            GeoPoint p1 = new GeoPoint(this,ray.getPoint(t1));
            GeoPoint p2 =  new GeoPoint(this,ray.getPoint(t2));
            return List.of(p1, p2);
        }

        if(t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        if(t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return null; // no intersections at all
    }
}