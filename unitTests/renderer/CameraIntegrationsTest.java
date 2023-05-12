package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import primitives.Vector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dan Zilberstein
 */
class CameraIntegrationsTest {

    static final Point ZERO_POINT = new Point(0, 0, 0);
    /**
     * Test helper function to count the intersections and compare with expected value
     *
     * @author Dan Zilberstein
     * @param cam      camera for the test
     * @param geo      3D body to test the integration of the camera with
     * @param expected amount of intersections
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        int count = 0;

        List<Point> allpoints = null;

        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX =3;
        int nY =3;
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                var intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (allpoints == null) {
                        allpoints = new LinkedList<>();
                    }
                    allpoints.addAll(intersections);
                }
                count += intersections == null ? 0 : intersections.size();
            }
        }

        System.out.format("there is %d points:%n", count);
        if (allpoints != null) {
            for (var item : allpoints) {
                System.out.println(item);
            }
        }
        System.out.println();

        assertEquals(expected, count, "Wrong amount of intersections");
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0));
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0));
        // TC01: Small Sphere 2 points
        assertCountIntersections(cam1, new Sphere(1, new Point(0, 0, -3)), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(cam2, new Sphere(2.5, new Point(0, 0, -2.5)), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(cam2, new Sphere(2, new Point(0, 0, -2)), 10);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(cam2, new Sphere(4, new Point(0, 0, -1)), 9);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(cam1, new Sphere(0.5, new Point(0, 0, 1)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    void cameraRayPlaneIntegration() {
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Plane against camera 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    void cameraRayTriangleIntegration() {
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle 1 point
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2)), 2);
    }

}