package primitives;
import static java.lang.Math.sqrt;

public class Vector extends Point {

    /**
     * Constructor: Initializes a new instance of the Vector class.
     *
     * @param coordinate A Double3 object representing the coordinates of the vector.
     * @throws IllegalArgumentException If the coordinate is zero vector.
     */
    Vector(Double3 coordinate) {
        super(coordinate);
        if (coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    /**
     * Constructor: Initializes a new instance of the Vector class.
     *
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     * @param z The z coordinate of the vector.
     * @throws IllegalArgumentException If the coordinate is zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.coordinate.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't define zero vector");
    }

    /**
     * Override: Determines whether two Vector objects are equal.
     *
     * @param o The other Vector object to compare with.
     * @return True if the two Vector objects are equal, False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Override: Gets a string representation of the Vector object.
     *
     * @return A string representation of the Vector object.
     */
    @Override
    public String toString() {
        return "Vector{" +
                "coordinate=" + coordinate +
                '}';
    }

    /**
     * Adds the given vector to this vector and returns the resulting vector.
     *
     * @param v The Vector object to add.
     * @return A new Vector object representing the result of the addition.
     */
    public Vector add(Vector v) {
        return new Vector(super.add(v).coordinate);
    }

    /**
     * Scales the vector by the given factor and returns the resulting vector.
     *
     * @param d The factor to scale the vector by.
     * @return A new Vector object representing the scaled vector.
     */
    public Vector scale(double d) {
        return new Vector(this.coordinate.scale(d));
    }

    /**
     * Calculates and returns the dot product of this vector and the given vector.
     *
     * @param v The Vector object to compute the dot product with.
     * @return A double value representing the dot product of the two vectors.
     */
    public double dotProduct(Vector v) {
        return this.coordinate.d1*v.coordinate.d1 +
                this.coordinate.d2*v.coordinate.d2 +
                this.coordinate.d3*v.coordinate.d3;
    }

    /**
     * Calculates and returns the cross product of this vector and the given vector.
     *
     * @param v The Vector object to compute the cross product with.
     * @return A new Vector object representing the cross product of the two vectors.
     */
    public Vector crossProduct(Vector v) {
        double d1 =  this.coordinate.d2*v.coordinate.d3 - this.coordinate.d3*v.coordinate.d2;
        double d2 =  this.coordinate.d3*v.coordinate.d1 - this.coordinate.d1*v.coordinate.d3;
        double d3 =  this.coordinate.d1*v.coordinate.d2 - this.coordinate.d2*v.coordinate.d1;
        return new Vector(d1,d2,d3);
    }

    /**
     * Computes and returns the squared length of the vector.
     *
     * @return A double value representing the squared length of the vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Computes and returns the length of the vector.
     *
     * @return A double value representing the length of the vector.
     */
    public double length() {
        return sqrt(this.lengthSquared());
    }

    /**
     * Normalizes the vector and returns the resulting vector.
     *
     * @return A new Vector object representing the normalized vector.
     */
    public Vector normalize() {
        double length = this.length();
        return new Vector(this.coordinate.d1/length, this.coordinate.d2/length, this.coordinate.d3/length);
    }
}
