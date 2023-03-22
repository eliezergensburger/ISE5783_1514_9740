package primitives;

import java.util.Objects;

public class Ray {
    final private Point p0;
    final private Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }
}
