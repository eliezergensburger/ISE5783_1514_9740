package primitives;

import static java.lang.Math.sqrt;

/**
 * This class represents a point in space.
 */
public class Point {

    public static Point ZERO = new Point(Double3.ZERO);
    /**
     * The coordinate
     */
    final Double3 coordinate;
    /**
     * Constructs a Point object with the specified x, y, and z coordinates.
     *
     * @param coordinate a Double3 value representing the coordinates of the point.
     */
    Point(Double3 coordinate) {
        this.coordinate = coordinate;
    }

    public Point(double x, double y, double z) {
        this.coordinate = new Double3(x, y, z);
    }

    public Double3 getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "Point:\n" +
                "\tcoordinate=" + coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Point other)
                && this.coordinate.equals(other.coordinate);
    }

    /**
     * Returns a new Point object that is the result of adding the specified Vector object to this Point object.
     *
     * @param v the Vector object to add to this Point object.
     * @return a new Point object that is the result of adding the specified Vector object to this Point object.
     */
    public Point add(Vector v) {
        return new Point(this.coordinate.add(v.coordinate));
    }

    /**
     * Returns a new Vector object that is the result of subtracting the specified Point object to this Point object.
     *
     * @param p the Point object to subtract from this Point object.
     * @return a new Vector object that is the result of subtracting the specified Point object from this Point object.
     */
    public Vector subtract(Point p) {
        return new Vector(this.coordinate.subtract(p.coordinate));
    }

    /**
     * Returns the squared distance between two points.
     *
     * @param p the Point object to calculate the squared distance from.
     * @return the squared distance between the points.
     */
    public double distanceSquared(Point p) {
        Double3 temp = this.coordinate.subtract(p.coordinate);
        return temp.d1 * temp.d1 + temp.d2 * temp.d2 + temp.d3 * temp.d3;
    }

    /**
     * Returns the distance between two points.
     *
     * @param p the Point object to calculate the distance from.
     * @return the distance between the points.
     */
    public double distance(Point p) {
        return sqrt(this.distanceSquared(p));
    }


    public Point min(Point p) {
        double x = Math.min(this.coordinate.getX(), p.coordinate.getX());
        double y = Math.min(this.coordinate.getY(), p.coordinate.getY());
        double z = Math.min(this.coordinate.getZ(), p.coordinate.getZ());
        return new Point(x, y, z);
    }

    public Point max(Point p) {
        double x = Math.max(this.coordinate.getX(), p.coordinate.getX());
        double y = Math.max(this.coordinate.getY(), p.coordinate.getY());
        double z = Math.max(this.coordinate.getZ(), p.coordinate.getZ());
        return new Point(x, y, z);
    }
}
