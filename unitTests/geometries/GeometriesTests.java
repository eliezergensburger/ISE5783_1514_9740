package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testAdd() {
    }

    @Test
    void testFindIntersections() {
        //TODO: implement this tests!
        Ray r1 = new Ray(new Point(30,30,30), new Vector(1,2,3));
        Ray r2 = new Ray(new Point(-5,1,1), new Vector(-3,2,3));
        Ray r3 = new Ray(new Point(2,0,1.5), new Vector(3,8,5.5));
        Ray r4 = new Ray(new Point(2.1,1,2), new Vector(-1,3,3));

        Plane p1 = new Plane(new Point(-1,-1,-1),new Point(-1,-1,-2),new Point(-1,-2,-1));
        Plane p2 = new Plane(new Point(1,1,1),new Point(3,1,2),new Point(1,2,5));
        Sphere s1 = new Sphere(2, new Point(-10,-10,-10));
        Sphere s2 = new Sphere(1, new Point(2,2,2));
        Sphere s3 = new Sphere(1, new Point(2,1,2));

        //Triangle t = new Triangle(new Point(0,0,0),new Point(1,1,1),new Point(4,5,6));
        Geometries g = new Geometries(List.of(p1,p2,s1,s2));

        //tc11: empty List of shapes.
        assertNull(new Geometries().findIntersections(r1), "tc11: empty List of shapes.");
        //tc12: no intersection with any of the shapes.
        assertNull(g.findIntersections(r1), "tc12: no intersection with any of the shapes.");
        //tc13: only one shapes intersects.
        assertEquals(1, g.findIntersections(r2).size(), "tc13: only one shapes intersects");
        //tc14: sum of the shapes intersects and some don't.
        assertEquals(3, g.findIntersections(r3).size(), "tc14: sum of the shapes intersects and some don't");
        //tc15: all shapes intersects in scene.
        assertEquals(3, new Geometries(List.of(p1,s3,p2)).findIntersections(r4).size(), "tc15: all shapes intersects in scene");

    }
}