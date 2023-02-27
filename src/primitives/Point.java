package primitives;

import java.util.Objects;

public class Point {
    final Double3 cordinate;

    public Point(Double3 cordinate) {
        this.cordinate = cordinate;
    }
    Point(double x, double y, double z) {
        this.cordinate = new Double3(x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // ?יכול להיות שזה מיותר
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return this.cordinate.equals(point);
    }
}
