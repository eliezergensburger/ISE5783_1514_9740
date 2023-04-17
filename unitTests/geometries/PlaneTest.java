package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}





