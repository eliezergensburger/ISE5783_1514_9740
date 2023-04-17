package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    @Test
    void testEquals() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(1.0, 2.0, 3.0);
        Vector v3 = new Vector(4.0, 5.0, 6.0);
        assertTrue(v1.equals(v2), "equals method is incorrect");
        assertFalse(v1.equals(v3), "equals method is incorrect");
    }

    @Test
    void testAdd() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        Vector v3 = v1.add(v2);
        assertEquals(new Vector(5.0, 7.0, 9.0), v3, "Addition of Vectors is incorrect");
    }

    @Test
    void testScale() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = v1.scale(2.0);
        assertEquals(new Vector(2.0, 4.0, 6.0), v2, "Scaling of Vector is incorrect");
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        double dotProduct = v1.dotProduct(v2);
        assertEquals(32.0, dotProduct, 0.0001, "Dot product of Vectors is incorrect");
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        Vector crossProduct = v1.crossProduct(v2);
        assertEquals(new Vector(-3.0, 6.0, -3.0), crossProduct, "Cross product of Vectors is incorrect");
    }

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