package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Tube tu = new Tube(1 ,new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: tests for calculation of normal to the tu
        assertEquals(new Vector(0, 0, 1),
                tu.getNormal(new Point(1, 0, 1)),
                "ERROR: The calculation of normal to the tu is not calculated correctly");

        // =============== Boundary Values Tests ==================
        // TC02: Test when the point is orthogonal to the ray's head goes to the ZERO vector
        assertThrows(IllegalArgumentException.class, () -> tu.getNormal(new Point(0, 0, 1)),
                "ZERO vector is not allowed");
    }
}
