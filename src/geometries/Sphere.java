package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    final private Point center; // The center point of the sphere

    /**
     * Constructor: Initializes a new instance of the Sphere class.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the surface of the sphere at the given point.
     *
     * @param p The Point object representing the point on the surface.
     * @return A Vector object representing the normal vector to the surface at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
