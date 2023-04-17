package Gometries;

public abstract class RadialGeometry implements Geometries.Geometry {
    final protected double radius;

    /**
     * Constructor: Initializes a new instance of the RadialGeometry class.
     *
     * @param radius The radius of the shape.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
