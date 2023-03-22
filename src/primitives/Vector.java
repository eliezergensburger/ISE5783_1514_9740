package primitives;
import static java.lang.Math.sqrt;

public class Vector extends Point {
    Vector(Double3 coordinate) {
        super(coordinate);
        if (coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "coordinate=" + coordinate +
                '}';
    }

    public Vector add(Vector v) {
        return new Vector(super.add(v).coordinate);
    }
    public Vector scale(double d) {
        return new Vector(this.coordinate.scale(d));
    }

    public double dotProduct(Vector v) {
        return this.coordinate.d1*v.coordinate.d1 +
                this.coordinate.d2*v.coordinate.d2 +
                this.coordinate.d3*v.coordinate.d3;
    }

    public Vector crossProduct(Vector v) {
        double d1 =  this.coordinate.d2*v.coordinate.d3 - this.coordinate.d3*v.coordinate.d2;
        double d2 =  this.coordinate.d3*v.coordinate.d1 - this.coordinate.d1*v.coordinate.d3;
        double d3 =  this.coordinate.d1*v.coordinate.d2 - this.coordinate.d2*v.coordinate.d1;
        return new Vector(d1,d2,d3);
    }

    public double lengthSquared() {
        return this.dotProduct(this);
    }

    public double length() {
        return sqrt(this.lengthSquared());
    }

    public Vector normalize() {
        double length = this.length();
        return new Vector(this.coordinate.d1/length, this.coordinate.d2/length, this.coordinate.d3/length);
    }
}
