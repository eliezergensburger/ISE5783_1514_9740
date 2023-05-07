package renderer;

import primitives.*;

/**
 * This class represents a camera in a 3D scene.
 */
public class Camera {
    private Point location;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height;
    private double width;
    private double distance;

    /**
     * Creates a new Camera object with the given location, direction of view, and up vector.
     *
     * @param location the camera's location
     * @param vTo the camera's direction of view
     * @param vUp the camera's up vector
     * @throws IllegalArgumentException if the given vTo and vUp vectors are not orthogonal
     */
    public Camera(Point location, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (vUp.dotProduct(vTo) != 0) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Gets the height of the viewport.
     *
     * @return the height of the viewport
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the viewport.
     *
     * @return the width of the viewport
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance of the viewport from the camera.
     *
     * @return the distance of the viewport from the camera
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the size of the viewport.
     *
     * @param width the width of the viewport
     * @param height the height of the viewport
     * @return this camera object
     * @throws IllegalArgumentException if width or height are not positive
     */
    public Camera setVPSize(double width, double height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width and height must be bigger than 0");
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * Sets the distance of the viewport from the camera.
     *
     * @param distance the distance of the viewport from the camera
     * @return this camera object
     * @throws IllegalArgumentException if distance is not positive
     */
    public Camera setVPDistance(double distance) throws IllegalArgumentException {
        if (distance <= 0)
            throw new IllegalArgumentException("distance must be bigger than 0");
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray that passes through the given pixel coordinates in the viewport.
     *
     * @param nX the number of pixels along the viewport's width
     * @param nY the number of pixels along the viewport's height
     * @param j the column index of the pixel
     * @param i the row index of the pixel
     * @return a ray passing through the given pixel coordinates in the viewport
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        double Rx = height/nY;
        double Ry = width/nX;

        double yi = -(i - (nY - 1) / 2.0) * Ry;
        double xj = (j - (nX - 1) / 2.0) * Rx;

        Point PCenter = location.add(vTo.scale(distance));
        Point pIJ = PCenter;
        if(xj != 0) pIJ = pIJ.add(vRight.scale(xj));
        if (yi != 0) pIJ = pIJ.add(vUp.scale(yi));
        Ray result = new Ray(location, pIJ.subtract(location));
        return result;
    }
}
