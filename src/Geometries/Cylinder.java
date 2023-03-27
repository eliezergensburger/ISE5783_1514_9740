package Geometries;

import primitives.Ray;

public class Cylinder extends Tube{

    final private double height; // The height of the cylinder

    /**
     * Constructor: Initializes a new instance of the Cylinder class.
     *
     * @param radius The radius of the cylinder.
     * @param axisRay The axis ray of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }
}
