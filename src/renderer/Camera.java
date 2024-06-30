package renderer;


import geometries.Intersectable;
import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represented a camera
 *
 * @author Yisrael Jacob & Avraham Meiri
 */
public class Camera {

    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";
    private final Vector vRight;
    private final Vector vTo;
    private final Vector vUp;
    private final Point p0;
    int n;
    private double distance;
    private int width;
    private int height;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private double depthOfField;
    private double aperture;
    private PixelManager pixelManager;
    private boolean splitToThreads = false;

    /**
     * This is the constructor of the camera class. It takes 3 vectors as parameters and checks if they are orthogonal.
     * If they are not, it throws an exception.
     *
     * @param p0  The point that the camera spot in
     * @param vTo The direction of the camera
     * @param vUp The direction of the camera
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vUp and vTo aren't orthogonal");
        }
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * Getter for the distance field of Camera
     *
     * @return The distance between the camera and the viewPlane.
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Getter for the width field of Camera.
     *
     * @return The width of the camera.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for the height field of Camera.
     *
     * @return The height of the camera.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the distance between the camera and the viewPlane.
     *
     * @param distance The distance from the camera to the view plane.
     * @return The camera object itself.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }


    public Camera setMultithreading(boolean bool) {
        this.splitToThreads = bool;
        return this;
    }

    /**
     * Set the viewPlane size to the given width and height.
     *
     * @param width  The width of the viewPlane.
     * @param height The height of the viewPlane.
     * @return The camera object itself.
     */
    public Camera setVPSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * This function sets the image writer of the camera and returns the camera.
     *
     * @param imageWriter The ImageWriter object that will be used to write the image to a file.
     * @return The camera itself.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }


    /**
     * This function sets the ray tracer for this camera.
     *
     * @param rayTracer The ray tracer to use.
     * @return The camera object itself.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public double getDepthOfField() {
        return depthOfField;
    }

    public Camera setDepthOfField(double _depthOfField) {
        this.depthOfField = _depthOfField;
        return this;
    }

    public double getAperture() {
        return aperture;
    }

    public Camera setAperture(double aperture) {
        this.aperture = aperture;
        return this;
    }

    public Camera setGridParams(int n) {
        this.n = n;
        return this;
    }

    /**
     * Calculates the degree of freedom (dof) for a given point in a three-dimensional space.
     *
     * @param point The point for which to calculate the dof.
     * @return The dof value for the given point.
     */
    public double getDofByPoint(Point point) {
        Point vpCenterPoint = p0.add(vTo.scale(distance));
        Vector v = point.subtract(p0);
        Point p2 = vpCenterPoint.add(vUp);
        Point p3 = vpCenterPoint.add(vRight);

        Plane plane = new Plane(vpCenterPoint, p2, p3);

        List<Intersectable.GeoPoint> l = plane.findGeoIntersections(new Ray(p0, v));

        Point point1 = l.get(0).point;
        return point1.distance(point);
    }

    /**
     * Construct a ray through the pixel [i,j] on the view plane,
     * when the view plane is divided into nX by nY rectangular cells
     *
     * @param nX number of pixels in the columns
     * @param nY number of pixels in the rows
     * @param j  column index of the pixel
     * @param i  the row index of the pixel
     * @return A ray from the camera to the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Ratio (pixel width & height)
        double rY = (double) height / nY;
        double rX = (double) width / nX;

        // Image center

        // Pixel[i,j] center
        Point Pij = p0.add(vTo.scale(distance));

        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        // move to middle of pixel i,j
        if (!isZero(xJ))
            Pij = Pij.add(vRight.scale(xJ));
        if (!isZero(yI))
            Pij = Pij.add(vUp.scale(yI));

        // return ray from camera to viewPlane coordinate (i, j)
        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * Casts a ray for a specific pixel in the image and writes the resulting color to the image.
     *
     * @param nX  The x-coordinate of the pixel in the image.
     * @param nY  The y-coordinate of the pixel in the image.
     * @param col The column index of the pixel in the image.
     * @param row The row index of the pixel in the image.
     */
    private void castRay(int nX, int nY, int col, int row) {
        imageWriter.writePixel(col, row, rayTracer.traceRay(constructRay(nX, nY, col, row)));
        pixelManager.pixelDone();
    }

    /**
     * > The function iterates over all the pixels in the image and casts a ray through each pixel
     */
    public Camera renderImage() {
        // Checks that imageWriter and rayTracer fields isn't empty
        if (this.imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (this.rayTracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);
        // Rendering the image
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();

        pixelManager = new PixelManager(nY, nX, 1);
        PixelManager.Pixel pixel;


        if (!splitToThreads) {
            for (; (pixel = pixelManager.nextPixel()) != null; pixelManager.pixelDone())
                castRay(nX, nY, pixel.col(), pixel.row());
        } else {
            //rendering image with using of threads
            IntStream.range(0, nY).parallel().forEach(row ->
                    IntStream.range(0, nX).parallel().forEach(col -> castRay(nX, nY, col, row)));
        }
        return this;
    }

    /**
     * Render the image with implementation of the depth of field
     */
    public Camera renderImageWithDepthOfField() {
        if (imageWriter == null)
            throw new MissingResourceException("You need to enter a image writer", ImageWriter.class.getName(), "");
        if (rayTracer == null)
            throw new MissingResourceException("You need to enter a ray tracer", RayTracerBase.class.getName(), "");

        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, 1);
        PixelManager.Pixel pixel;


        if (!splitToThreads) {
            for (; (pixel = pixelManager.nextPixel()) != null; pixelManager.pixelDone()) {
                Ray myRay = constructRay(nX, nY, pixel.col(), pixel.row());
                List<Ray> myRays = constructRaysGridFromCamera(n, myRay);
                Color myColor = new Color(0, 0, 0);
                for (Ray ray : myRays) { // we pass in the list myRays and for each ray we found his color
                    myColor = myColor.add(rayTracer.traceRay(ray)); // we add the color of each ray to myColor
                }
                imageWriter.writePixel(pixel.col(), pixel.row(), myColor.reduce(myRays.size())); // we reduce myColor with the size of my list (number of rays)
            }
        } else {
            //rendering image with using of threads
            IntStream.range(0, nY).parallel().forEach(row ->
                    IntStream.range(0, nX).parallel().forEach(col -> {
                        Ray myRay = constructRay(nX, nY, col, row);
                        List<Ray> myRays = constructRaysGridFromCamera(n, myRay);
                        Color myColor = new Color(0, 0, 0);
                        for (Ray ray : myRays) { // we pass in the list myRays and for each ray we found his color
                            myColor = myColor.add(rayTracer.traceRay(ray)); // we add the color of each ray to myColor
                        }
                        imageWriter.writePixel(col, row, myColor.reduce(myRays.size())); // we reduce myColor with the size of my list (number of rays)
                    })
            );
        }
        return this;
    }

    /**
     * @param ray the ray from the center of the camera
     * @param n   the number of squares in height and width (of the grid)
     *            this function construct a grid around the circle of the camera
     * @return the list of all the rays from each pixel in the camera
     */
    public List<Ray> constructRaysGridFromCamera(int n, Ray ray) { // we construct a square around the circle of the camera, the size n=2*radius(aperture)
        // we launch a ray (we choose a random _focusPoint in the pixel)  from each pixel of the grid, and
        // we select only the ray IN the circle of the camera

        List<Ray> myRays = new LinkedList<>(); //the list of all the rays

        double t0 = depthOfField + distance; // distance from the central focusPoint of the camera to the focus focusPoint
        double t = t0 / (vTo.dotProduct(ray.getDir())); // distance from the focusPoint on the aperture grid to the focus focusPoint ( found with the cosines)
        Point focusPoint = ray.getPoint(t); // we found the focus focusPoint

        double pixelSize = alignZero((aperture * 2) / n); // the size of each pixel

        // we construct a ray from each pixel of the grid, and we select only the rays in the circle
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Ray tmpRay = constructRayFromPixel(n, n, j, i, pixelSize, focusPoint);
                // we check if each ray is in the circle of the camera
                if (tmpRay.getP0().equals(p0)) {// if the ray is from the camera center
                    myRays.add(tmpRay); // we add the ray to the list myRays
                } else if (tmpRay.getP0().subtract(p0).dotProduct(tmpRay.getP0().subtract(p0)) <= aperture * aperture) {
                    // if the distance with the center (squared) is <= the square of the radius -> the ray is in the circle of the camera
                    myRays.add(tmpRay); // we add the ray to the list myRays
                }
            }
        }
        return myRays; // we return  the list of all my rays in the circle
    }

    /**
     * @param nX         grid's width
     * @param nY         grid's height
     * @param j          y emplacement of the point
     * @param i          x emplacement of the point
     * @param pixelSize  size of the pixel
     * @param focusPoint point on the focus plane
     * @return a ray from the pixel to the focus point
     */
    private Ray constructRayFromPixel(int nX, int nY, double j, double i, double pixelSize, Point focusPoint) {

        Point pIJ = p0;

        Random r = new Random(); // we want a random point for each pixel for more precision

        double xJ = ((j + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((nX - 1) / 2d)) * pixelSize;
        double yI = -((i + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((nY - 1) / 2d)) * pixelSize;

        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        Vector vIJ = focusPoint.subtract(pIJ);

        return new Ray(pIJ, vIJ); // return a new ray from a pixel
    }

    /**
     * If the imageWriter is not null, write to the image.
     */
    public void writeToImage() {
        if (this.imageWriter == null) {
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        }
        this.imageWriter.writeToImage();
    }

    /**
     * This function prints a grid on the image, with the given interval and color.
     *
     * @param interval the interval between the lines
     * @param color    the color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null) {
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        }
        // Prints the grid
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        // The row of the pixel in the image.
        for (int row = 0; row < nY; row++) {
            // The column of the pixel in the image.
            for (int col = 0; col < nX; col++) {
                if (row % interval == 0 || col % interval == 0)
                    this.imageWriter.writePixel(row, col, color);
            }
        }
    }


}

