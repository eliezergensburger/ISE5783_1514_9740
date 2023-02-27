package primitives;

public class Point {
    final Double3 coordinate;

    public Point(Double3 coordinate) {
        this.coordinate = coordinate;
    }
    Point(double x, double y, double z) {
        this.coordinate = new Double3(x,y,z);
    }

    @Override
    public String toString() {
        return "Point:\n" +
                "\tcoordinate=" + coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // ?יכול להיות שזה מיותר
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return this.coordinate.equals(point);
    }
    public Point add(Vector v) {
        return new Point(this.coordinate.add(v.coordinate));
    }
}
