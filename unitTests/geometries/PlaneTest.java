package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {

    @Test
    void testConstractor(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try{
            new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        }catch (IllegalArgumentException e){
            // This is a fail test. It is a test that is expected to fail.
            fail("The constarctor throw error for nothing");
        }
        // ============ Boundary Values Tests =============
        // TC02: Test when all points arr equal.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC03: Test when b point equals to a point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(2,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC04: Test when a point equals to c point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(2,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC05: Test when b point equals to c point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(2,0,0),new Point(1,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC06: Test when all points arr on the same line.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(2,0,0),new Point(3,0,0)),
                "The constractor dont throw error exception for the  three points in the same line");

    }


    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     * 1.checks if it is on the plane
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
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

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray into plane(1 point)
        assertEquals(
                List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.51, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");


        // TC02: Ray out of plane(0 points)
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // **** Group: ray parallel to the plane
        // TC11: Ray parallel to plane(0 points)
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: Ray in plane(0 points)
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // **** Group: ray perpendicular to the plane
        // TC13: Orthogonal ray befor plane
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Orthogonal ray in plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 2, 2), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // **** Group: ray that is neither parallel nor perpendicular to the plane but starts inside the plane
        // TC16: Orthogonal ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // **** Similar to the previous case, but the beginning of the beam exactly at the "reference point" of
        //the plain
        // TC17: Ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

    }
}