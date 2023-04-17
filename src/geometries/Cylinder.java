package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    final private double height; // The height of the cylinder

    public double getHeight() {
        return this.height;
    }
    public Cylinder(Ray axisRay, double radius, double height ) {
        super(radius, axisRay);
        this.height = height;
    }
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Cylinder) &&
                super.equals(obj)&&  this.height == ((Cylinder) obj).height;
    }
    @Override
    public Vector getNormal(Point point) {

        // define the center of cylinder's sides
        Vector cylinderCenterVector = axisRay.getDir();

        Point centerOfOneSide = axisRay.getP0();
        Point centerOfSecondSide = axisRay.getP0().add(axisRay.getDir().scale(height));

        //The normal at a base will be simply equal to central ray's
        //direction vector 𝑣 or opposite to it (−𝑣) so we check it
        if (point.equals(centerOfOneSide)) {
            return cylinderCenterVector.scale(-1);
        }
        else if (point.equals(centerOfSecondSide)){
            return cylinderCenterVector;
        }

        //If the point on one of the cylinder's bases, but it's not the center point
        double projection = cylinderCenterVector.dotProduct(point.subtract(centerOfOneSide));
        if (projection == 0) {
            Vector v1 = point.subtract(centerOfOneSide);
            return v1.normalize();
        }

        //If the point on the side of the cylinder.
        Point center = centerOfOneSide.add(cylinderCenterVector.scale(projection));
        Vector v = point.subtract(center);

        return v.normalize();
    }
}