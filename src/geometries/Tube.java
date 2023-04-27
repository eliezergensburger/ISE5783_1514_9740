package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry {

    final protected Ray axisRay; // The axis ray of the tube

    /**
     * Constructor: Initializes a new instance of the Tube class.
     *
     * @param radius  The radius of the tube.
     * @param axisRay The axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the normal vector to the surface of the tube at the given point.
     *
     * @param point The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        //by the formulla in the slides
        double t = axisRay.getDir().dotProduct(
                point.subtract(
                        axisRay.getP0()));
        Point O = axisRay.getP0().add(
                axisRay.getDir().scale(t));
        return point.subtract(O).normalize();
    }

    /**
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}