package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 *  Interface for finding intersections points
 *
 *  @author Israel JAcob & Avraham Meiri
 */
public abstract class Intersectable {
    protected boolean bvhIsOn = false;  //a field to turn on and off the bvh

    public BoundingBox box; //Boundary box

    /**
     * This class represent geometric body and point in it.
     */
    public static class GeoPoint{

        public final Geometry geometry;
        public final Point point;


        /**

         Constructs a GeoPoint object with the specified geometry and point.
         @param geometry The geometry to which the point belongs.
         @param point The coordinates of the point.
         */
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }


    /**
     * @param b boolean value for bvh
     * @return this (using builder pattern)
     */
    public void setBvhIsOn(boolean b) {
        if (!bvhIsOn && b)//no box has been created
            createBoundingBox();
        bvhIsOn=b;
    }
    /**
     * Returns all the intersections of ray with geometry shape
     *
     * @param ray {@link Ray} pointing toward the object
     * @return List of intersection {@link Point}s
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }


    /**
     * This function returns a list of all the intersections of the ray with the geometry of the scene
     *
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoints.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
        if (bvhIsOn && ! isIntersectingBoundingBox(ray))    //We'll only calculate intersections if it intersects bounding box
            return null;
        return findGeoIntersectionsHelper(ray);
    }


    /**
     * Returns a list of GeoPoints where a ray intersects with.
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @param maxDistance The maximum distance to search for intersections.
     * @return A list of GeoPoints.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }


    /**
     * Finds the intersection points of the ray with the surface of the object
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Finds the intersection points of the ray with the surface of the object
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @param maxDistance The maximum distance from the source of the ray to intersect with.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * class representing boundary box
     */
    public static class BoundingBox {
        public Point minimums;  //Borders of box
        public Point maximums;  //Box borders

        public BoundingBox(Point mins, Point maxs) {
            minimums = mins;
            maximums = maxs;
        }

    }
    /**
     * Creates bounding box for objects
     */
    public abstract void createBoundingBox();

    /**
     * Testing if ray intersects
     *
     * @param ray ray to check
     * @return Whether ray intersects box
     * Code taken from scratchapixel.com
      */
    public boolean isIntersectingBoundingBox(Ray ray) {
        if (!bvhIsOn || box == null)    //Intersect as usual
            return true;
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();
        double tMin = (box.minimums.getCoordinate().getX() - p0.getCoordinate().getX()) / dir.getCoordinate().getX();
        double tMax = (box.maximums.getCoordinate().getX() - p0.getCoordinate().getX()) / dir.getCoordinate().getX();
        if (tMin > tMax) {
            double temp = tMin;
            tMin = tMax;
            tMax = temp;
        }
        double tyMin = (box.minimums.getCoordinate().getY() - p0.getCoordinate().getY()) / dir.getCoordinate().getY();
        double tyMax = (box.maximums.getCoordinate().getY() - p0.getCoordinate().getY()) / dir.getCoordinate().getY();
        if (tyMin > tyMax) {    //Swapping if the bottom is larget than the top
            double temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }
        if ((tMin > tyMax) || (tyMin > tMax))
            return false;
        if (tyMin > tMin)
            tMin = tyMin;
        if (tyMax < tMax)
            tMax = tyMax;
        double tzMin = (box.minimums.getCoordinate().getZ() - p0.getCoordinate().getZ()) / dir.getCoordinate().getZ();
        double tzMax = (box.maximums.getCoordinate().getZ() - p0.getCoordinate().getZ()) / dir.getCoordinate().getZ();
        if (tzMin > tzMax) {
            double temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
        }
        if ((tMin > tzMax) || (tzMin > tMax))
            return false;
        if (tzMin > tMin)
            tMin = tzMin;
        if (tzMax < tMax)
            tMax = tzMax;
        return true;
    }

}
