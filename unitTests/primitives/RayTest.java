package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RayTest {
    Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 10, -100));
    List<Point> list;

    @Test
    void testFindClosestPoint()
    {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Point in the middle of the list is the closest
        list = new LinkedList<Point>();
        list.add(new Point(1, 1, -100));
        list.add(new Point(0, 2, -10));
        list.add(new Point(0.5, 0, -100));

        assertEquals(
                list.get(1),
                ray.findClosestPoint(list),
                "Closest point should be in the middle");

        // =============== Boundary Values Tests ==================//

        // TC11: Empty list
        list = null;

        assertNull(
                ray.findClosestPoint(list),
                "Empty list should return null");

        // TC12: First point in the list is the closest
        list = new LinkedList<Point>();
        list.add(new Point(0, 2, -10));
        list.add(new Point(1, 1, -100));
        list.add(new Point(0.5, 0, -100));

        assertEquals(
                list.get(0),
                ray.findClosestPoint(list),
                "Closest point should be the first point");

        // TC13: Last point in the list is the closest
        list = new LinkedList<Point>();
        list.add(new Point(1, 1, -100));
        list.add(new Point(0.5, 0, -100));
        list.add(new Point(0, 2, -10));

        assertEquals(
                list.get(2),
                ray.findClosestPoint(list),
                "Closest point should be the last point");
    }
}