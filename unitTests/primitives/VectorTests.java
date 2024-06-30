package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Vector class.
 */
class VectorTests {

    /**
     * Tests the equals method of the Vector class.
     */
    @Test
    void testEquals() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(1.0, 2.0, 3.0);
        Vector v3 = new Vector(4.0, 5.0, 6.0);
        assertTrue(v1.equals(v2), "equals method is incorrect");
        assertFalse(v1.equals(v3), "equals method is incorrect");
    }

    /**
     * Tests the add method of the Vector class.
     */
    @Test
    void testAdd() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(-1, -1, -1).add(new Vector(1, 1, 1)),
                "ERROR: Vector + -itself does not throw an exception");
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        Vector v3 = v1.add(v2);
        assertEquals(new Vector(5.0, 7.0, 9.0), v3, "Addition of Vectors is incorrect");
    }

    /**
     * Tests the scale method of the Vector class.
     */
    @Test
    void testScale() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(-1, -1, -1).scale(0),
                "ERROR: Vector + -itself does not throw an exception");
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = v1.scale(2.0);
        assertEquals(new Vector(2.0, 4.0, 6.0), v2, "Scaling of Vector is incorrect");
    }

    /**
     * Tests the dotProduct method of the Vector class.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        double dotProduct = v1.dotProduct(v2);
        assertEquals(32.0, dotProduct, 0.0001, "Dot product of Vectors is incorrect");
    }

    /**
     * Tests the crossProduct method of the Vector class.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        Vector crossProduct = v1.crossProduct(v2);
        assertEquals(new Vector(-3.0, 6.0, -3.0), crossProduct, "Cross product of Vectors is incorrect");
    }

    /**
     * Tests the lengthSquared method of the Vector class.
     */
    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        double lengthSquared = v1.lengthSquared();
        assertEquals(14.0, lengthSquared, 0.0001, "Squared length of Vector is incorrect");
    }

    @Test
    void testLength() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        double length = v1.length();
        assertEquals(Math.sqrt(14.0), length, 0.0001, "Length of Vector is incorrect");
    }

    @Test
    void testNormalize() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector normalized = v1.normalize();
        assertEquals(1, normalized.length(), "Normalization of Vector is incorrect");
    }
}