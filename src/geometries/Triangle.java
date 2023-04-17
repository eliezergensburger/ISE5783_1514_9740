package geometries;

import primitives.Point;

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
}
