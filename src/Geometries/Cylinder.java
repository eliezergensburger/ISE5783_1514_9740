package Geometries;

import primitives.Ray;

public class Cylinder extends Tube{

    final private double height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }
}
