package primitives;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testEquals() {
        Point p1 = new Point(1.0, 2.0, 3.0);
        Point p2 = new Point(1.0, 2.0, 3.0);
        Point p3 = new Point(4.0, 5.0, 6.0);
        assertTrue(p1.equals(p2), "equals method is incorrect");
        assertFalse(p1.equals(p3), "equals method is incorrect");
    }

    @Test
    void testAdd() {
        assertThrows(IllegalArgumentException.class,
                () -> new Point(-1,-1,-1).subtract(new Point(1,1,1)),
                "ERROR: Vector + -itself does not throw an exception");
        Point p1 = new Point(1, 2, 3);
        assertEquals(
                new Point(0, 0, 0),
                p1.add(new Vector(-1, -2, -3)),
                "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1,1,1).subtract(new Point(1,1,1)),
                "ERROR: Vector - itself does not throw an exception");
        Point p1 = new Point(1.0, 2.0, 3.0);
        Point p2 = new Point(2.0, 3.0, 4.0);
        Vector v = p2.subtract(p1);
        assertEquals(new Vector(1.0, 1.0, 1.0), v, "ERROR: Point - Point does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        Point p1 = new Point(1.0, 2.0, 3.0);
        Point p2 = new Point(4.0, 5.0, 6.0);
        assertEquals(
                27.0, p1.distanceSquared(p2),
                0.0001,
                "ERROR: Squared distance between two Points is incorrect");
    }

    @Test
    void testDistance() {
        Point p1 = new Point(1.0, 2.0, 3.0);
        Point p2 = new Point(4.0, 5.0, 6.0);
        assertEquals(
                Math.sqrt(27.0),
                p1.distance(p2),
                0.0001,
                "ERROR: Distance between two Points is incorrect");
    }
}