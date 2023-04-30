package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon{

    /**
     * Constructor: Initializes a new instance of the Triangle class.
     *
     * @param p1 A Point object representing the first vertex of the triangle.
     * @param p2 A Point object representing the second vertex of the triangle.
     * @param p3 A Point object representing the third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        if((v.dotProduct(n1) > 0 && v.dotProduct(n2) > 0 && v.dotProduct(n3) > 0) ||
                (v.dotProduct(n1) < 0 && v.dotProduct(n2) < 0 && v.dotProduct(n3) < 0))
            return plane.findIntersections(ray);
        return null;
    }
}