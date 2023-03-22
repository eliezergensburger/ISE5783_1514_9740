package primitives;

public class Vector extends Point {
    public Vector(Double3 coordinate) {
        super(coordinate);
        if (coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    public Vector scale(double d) {
        return new Vector(this.coordinate.scale(d));
    }

}
