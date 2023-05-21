package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere  pl = new Sphere(1, new Point(0, 0, 0));
        assertEquals(new Vector(0, 0, 1),
                pl.getNormal(new Point(0, 0, 1)),
                "Bad normal to Sphere"); // check for point on the sphere
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
// ============ Equivalence Partitions Tests ==============
// TC01: Ray's line is outside the sphere (0 points) assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        List<Point> result = sphere.findIntersections(new Ray(new Point(7, 11, 0),
                new Vector(4, 5, 0)));
        assertNull(result, "Ray dosn't cross sphere");
// TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(-2.47688, 0.912116, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getCoordinate().getX() > result.get(1).getCoordinate().getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
// TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(1.5874236641150197,0.8092795801437747,0);
        result = sphere.findIntersections(new Ray(new Point(1.1, 0.2, 0),
                new Vector(4, 5, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");



// TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 3, 0),
                new Vector(3, 3.5, 0)));
        assertNull(result, "Ray dosn't cross sphere");

// =============== Boundary Values Tests ==================
// **** Group: Ray's line crosses the sphere (but not the center)
// TC11: Ray starts at sphere and goes inside (1 point)
        assertEquals(
                List.of(
                        new Point(2, 0, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, -1, 0),
                                new Vector(1, 1, 0))),
                "Ray from sphere inside");
// TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(2, 0, 0),
                                new Vector(1, -1, 0))),
                "Ray from sphere outside");
// **** Group: Ray's line goes through the center
// TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(
                new Ray(
                        new Point(1, -2, 0),
                        new Vector(0, 1, 0)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points");
        if (result.get(0).getCoordinate().getY() > result.get(1).getCoordinate().getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(
                List.of(
                        new Point(1, -1, 0),
                        new Point(1, 1, 0)),
                result,
                "Line through O, ray crosses sphere");
// TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals(
                List.of(
                        new Point(1, 1, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, -1, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from and crosses sphere");
// TC15: Ray starts inside (1 point)
        assertEquals(
                List.of(
                        new Point(1, 1, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 0.5, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from inside sphere");
// TC16: Ray starts at the center (1 point)
        assertThrows(
                IllegalArgumentException.class,
                () -> sphere.findIntersections(
                        new Ray(
                                new Point(1, 0, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from O");
// TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 1, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from sphere outside");
// TC18: Ray starts after sphere (0 points)
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 2, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray outside sphere");
// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
// TC19: Ray starts before the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(0, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray before sphere");
// TC20: Ray starts at the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray at sphere");
// TC21: Ray starts after the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(2, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray after sphere");
// **** Group: Special cases
// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(-1, 0, 0),
                                new Vector(0, 0, 1))),
                "Ray orthogonal to ray head -> O line");
    }
}