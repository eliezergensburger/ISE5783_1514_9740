package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
    Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
    Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: tests for calculation of normal to the sphere//
        Sphere sphere = new Sphere(5, new Point(0, 0, 0));

        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)),
                "ERROR: The calculation of normal to the Sphere is not calculated correctly");
    }

    /**
     * Test methods for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void allFindIntersectionality(){
        testFindIntersections1();
        testFindIntersections2();
        testFindIntersections3();
        testFindIntersections4();
        testFindIntersections5();
        testFindIntersections6();
        testFindIntersections7();
        testFindIntersections8();
        testFindIntersections9();
        testFindIntersections10();
        testFindIntersections11();
        testFindIntersections12();
        testFindIntersections13();
        testFindIntersections14();
        testFindIntersections15();
        testFindIntersections16();
    }

    // ============ Equivalence Partitions Tests ==============

    // TC01: Ray's line is outside the sphere (0 points)
    @Test
    public void testFindIntersections1() {
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(-1, 0, 0),
                                new Vector(1, 1, 0))),
                "Ray's line out of sphere");
    }

    // TC02: Ray starts before and crosses the sphere (2 points)
    @Test
    public void testFindIntersections2(){
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(),
                "Wrong number of points");
        if (result.get(0).getCoordinate().getX() > result.get(1).getCoordinate().getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result,
                "Ray crosses sphere");
    }

    // TC03: Ray starts inside the sphere (1 point)
    @Test
    void testFindIntersections3(){
        assertEquals(
                List.of(p2),
                sphere.findIntersections(
                        new Ray(
                                new Point(0.5, 0.5, 0),
                                new Vector(3, 1, 0))),
                "Ray from inside sphere");
    }

    // TC04: Ray starts after the sphere (0 points)
    @Test
    void testFindIntersections4(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(2, 1, 0),
                                new Vector(3, 1, 0))),
                "Sphere behind Ray");
    }

    // =============== Boundary Values Tests ==================//

    // **** Group: Ray's line crosses the sphere (but not the center)

    // TC11: Ray starts at sphere and goes inside (1 points)
    @Test
    void testFindIntersections5(){
        assertEquals(
                List.of(
                        new Point(2, 0, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, -1, 0),
                                new Vector(1, 1, 0))),
                "Ray from sphere inside");
    }

    // TC12: Ray starts at sphere and goes outside (0 points)
    @Test
    void testFindIntersections6(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(2, 0, 0),
                                new Vector(1, 1, 0))),
                "Ray from sphere outside");
    }

    // **** Group: Ray's line goes through the center

    // TC13: Ray starts before the sphere (2 points)
    @Test
    void testFindIntersections7(){
        List<Point> result = sphere.findIntersections(
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
    }

    // TC14: Ray starts at sphere and goes inside (1 points)
    @Test
    void testFindIntersections8() {
        assertEquals(
                List.of(
                        new Point(1, 1, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, -1, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from and crosses sphere");
    }

    // TC15: Ray starts inside (1 points)
    @Test
    void testFindIntersections9() {
        assertEquals(
                List.of(
                        new Point(1, 1, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 0.5, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from inside sphere");
    }

    // TC16: Ray starts at the center (1 points)
    @Test
    void testFindIntersections10(){
        assertEquals(
                List.of(
                        new Point(1, 1, 0)),
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 0, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from O");
    }

    // TC17: Ray starts at sphere and goes outside (0 points)
    @Test
    void testFindIntersections11(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 1, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray from sphere outside");
    }

    // TC18: Ray starts after sphere (0 points)
    @Test
    void testFindIntersections12(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 2, 0),
                                new Vector(0, 1, 0))),
                "Line through O, ray outside sphere");

    }

    // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

    // TC19: Ray starts before the tangent point
    @Test
    void testFindIntersections13(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(0, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray before sphere");
    }

    // TC20: Ray starts at the tangent point
    @Test
    void testFindIntersections14(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(1, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray at sphere");
    }

    // TC21: Ray starts after the tangent point
    @Test
    void testFindIntersections15(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(2, 1, 0),
                                new Vector(1, 0, 0))),
                "Tangent line, ray after sphere");
    }

    // **** Group: Special cases

    // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    @Test
    void testFindIntersections16(){
        assertNull(
                sphere.findIntersections(
                        new Ray(
                                new Point(-1, 0, 0),
                                new Vector(0, 0, 1))),
                "Ray orthogonal to ray head -> O line");
    }

}