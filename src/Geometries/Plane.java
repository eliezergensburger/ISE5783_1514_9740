package Geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final private Point p0;
    final  private Vector normal;

    public Plane(Point p1, Point p2, Point p3)
    {
        p0 = p1;
        normal = null;
    }
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
    public Vector getNormal() {
        return null;
    }
}
