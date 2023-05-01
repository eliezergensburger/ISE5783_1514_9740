package renderer;

import primitives.Point;
import primitives.Vector;

public class Camera {
    private Point location;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height;
    private double width;
    private double distance;

    public Camera(Point location, Vector vUp, Vector vTo) {
        if (vUp.dotProduct(vTo) != 0) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.location = location;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        vRight = vUp.crossProduct(vTo).normalize();
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera setVPSize(double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width and height must be bigger than 0");
        this.height = height;
        this.width = width;
        return this;
    }

    public Camera setVPDistance(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("distance must be bigger than 0");
        this.distance = distance;
        return this;
    }





}
