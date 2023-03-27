package primitives;
import java.util.Objects;

public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     * Constructor: Initializes a new instance of the Ray class.
     *
     * @param p0  A Point object representing the origin point of the ray.
     * @param dir A Vector object representing the direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Getter: Gets the origin point of the ray.
     *
     * @return A Point object representing the origin point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter: Gets the direction vector of the ray.
     *
     * @return A Vector object representing the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Override: Determines whether two Ray objects are equal.
     *
     * @param o The other Ray object to compare with.
     * @return True if the two Ray objects are equal, False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }
}
