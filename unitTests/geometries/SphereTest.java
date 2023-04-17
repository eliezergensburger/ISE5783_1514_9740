package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

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
        assertEquals(new Vector(0, 0, 1), pl.getNormal(new Point(0, 0, 1)), "Bad normal to Sphere"); // check for point on the sphere
    }
}