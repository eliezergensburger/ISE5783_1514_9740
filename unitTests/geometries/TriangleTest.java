package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {
    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle pl = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pl.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        Point[] pts = {new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)};
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Inside polygon/triangle(1 Point)
        Triangle triangle = new Triangle(
                new Point(2, 0, 0),
                new Point(0, 3, 0),
                new Point(0, 0, 0));

        Ray ray = new Ray(new Point(0, 0, -1), new Vector(1, 1, 1));
        List<Point> result = triangle.findIntersections(ray);
        Point p1 = new Point(1, 1, 0);
        assertEquals(List.of(p1), result, "Inside polygon/triangle(1 Point)");

        //TC02: Outside against edge(0 Point)
        triangle = new Triangle(
                new Point(2, 0, 0),
                new Point(0, 3, 0),
                new Point(0, 0, 0));
        ray = new Ray(new Point(0, 0, -1), new Vector(2, 1, 1));
        assertNull(triangle.findIntersections(ray), "Outside against edge");
    }
}