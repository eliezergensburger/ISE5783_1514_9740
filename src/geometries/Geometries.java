package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    private final List<Intersectable> items;

    BoundingBox box = null;
    Geometries left = null;
    Geometries right = null;

    Intersectable intersectable = null;

    /**
     * constructor of Geometry
     */
    public Geometries() {
        items = new LinkedList<>();
    }

    public Geometries(List<Intersectable> geometries) {
        if (bvhIsOn)
            createBoundingBox();
        items = geometries;
    }

    Geometries(BoundingBox boundingBox, Geometries left, Geometries right, List<Intersectable> geometries, Intersectable intersectable) {
        items = geometries;
        this.box = boundingBox;
        this.left = left;
        this.right = right;
        this.intersectable = intersectable;
    }

    public static Geometries buildBVH(Geometries geometries) {
        if (geometries.items.isEmpty()) {
            return null;
        }
        return recursiveBuildBVH(geometries, 0, geometries.items.size() - 1);
    }

    private static Geometries recursiveBuildBVH(Geometries geometries, int start, int end) {
        if (start == end) {
            // Create a leaf node
            Geometries myGeo = new Geometries(geometries.box, null, null, geometries.items,
                    geometries.items.get(0));
            myGeo.setBvhIsOn(true);
            return myGeo;
        }

        // Find the axis with the longest extent
        int longestAxis = findLongestAxis(geometries, end - start);

        // Sort geometries based on the longest axis
        geometries.items.sort(Comparator.comparingDouble(a -> getCentroid(a, longestAxis)));

        int mid = (start + end) / 2;

        // Recursively build left and right subtrees
        Geometries left = recursiveBuildBVH(new Geometries(geometries.items.subList(0, mid - start + 1)), start, mid);
        Geometries right = recursiveBuildBVH(new Geometries(geometries.items.subList(mid - start + 1, end - start + 1)), mid + 1, end);

        // Calculate the bounding box for the current node
        BoundingBox boundingBox = calculateBoundingBox(geometries.items, end - start);
        double minx = boundingBox.minimums.getCoordinate().getX() - 0.01;
        double miny = boundingBox.minimums.getCoordinate().getY() - 0.01;
        double minz = boundingBox.minimums.getCoordinate().getZ() - 0.01;
        double maxx = boundingBox.maximums.getCoordinate().getX() + 0.01;
        double maxy = boundingBox.maximums.getCoordinate().getY() + 0.01;
        double maxz = boundingBox.maximums.getCoordinate().getZ() + 0.01;

        // Create an internal node
        Geometries myGeo = new Geometries(boundingBox, left, right, geometries.items, new Polygon(
                new Point(minx, miny, minz),
                new Point(minx, miny, maxz),
                new Point(maxx, maxy, maxz),
                new Point(maxx, maxy, minz)));
        myGeo.setBvhIsOn(true);
        myGeo.intersectable.setBvhIsOn(true);
        return myGeo;
    }

    private static int findLongestAxis(Geometries geometries, int end) {
        double maxSAH = Double.POSITIVE_INFINITY;
        int bestAxis = 0;

        for (int axis = 0; axis < 3; axis++) {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;

            for (int i = 0; i <= end; i++) {
                double centroid = getCentroid(geometries.items.get(i), axis);
                min = Math.min(min, centroid);
                max = Math.max(max, centroid);
            }

            double extent = max - min;
            double sah = extent * (end + 1);

            if (sah < maxSAH) {
                maxSAH = sah;
                bestAxis = axis;
            }
        }

        return bestAxis;
    }

    private static double getCentroid(Intersectable geometry, int axis) {
        return switch (axis) {
            case 0 ->
                    (geometry.box.minimums.getCoordinate().getX() + geometry.box.maximums.getCoordinate().getX()) / 2.0;
            case 1 ->
                    (geometry.box.minimums.getCoordinate().getY() + geometry.box.maximums.getCoordinate().getY()) / 2.0;
            case 2 ->
                    (geometry.box.minimums.getCoordinate().getZ() + geometry.box.maximums.getCoordinate().getZ()) / 2.0;
            default -> throw new IllegalArgumentException("Invalid axis: " + axis);
        };
    }

    private static BoundingBox calculateBoundingBox(List<Intersectable> geometries, int end) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (int i = 0; i <= end; i++) {
            Intersectable geometry = geometries.get(i);
            minX = Math.min(minX, geometry.box.minimums.getCoordinate().getX());
            minY = Math.min(minY, geometry.box.minimums.getCoordinate().getY());
            minZ = Math.min(minZ, geometry.box.minimums.getCoordinate().getZ());
            maxX = Math.max(maxX, geometry.box.maximums.getCoordinate().getX());
            maxY = Math.max(maxY, geometry.box.maximums.getCoordinate().getY());
            maxZ = Math.max(maxZ, geometry.box.maximums.getCoordinate().getZ());
        }
        return new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }

    public List<Intersectable> getItems() {
        return items;
    }

    public void add(Intersectable... geometries) {
        if (bvhIsOn)
            createBoundingBox();
        items.addAll(List.of(geometries));
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * implementation of findGeoIntersectionsHelper from Intersectable
     *
     * @param ray {@link Ray}  pointing toward the object
     * @return List of intersection {@link Point}s
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (var item : this.items) {
            //get intersections points of a particular item from intersectables
            var points = item.findGeoIntersections(ray);
            if (points != null) {
                //first time initialize result to new LinkedList
                if (intersections == null)
                    intersections = new LinkedList<>();
                //add all item points to the resulting list
                intersections.addAll(points);
            }
        }
        return intersections;
    }

    /**
     * Finds the intersection points of the ray with the surface of the object
     *
     * @param ray         The ray to intersect with the GeoPoint.
     * @param maxDistance The maximum distance from the source of the ray to intersect with.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (var item : this.items) {
            //get intersections points of a particular item from intersectables
            var points = item.findGeoIntersections(ray, maxDistance);
            if (points != null) {
                //first time initialize result to new LinkedList
                if (intersections == null)
                    intersections = new LinkedList<>();
                //add all item points to the resulting list
                intersections.addAll(points);
            }
        }
        return intersections;
    }

    @Override
    public void createBoundingBox() {
        if (items == null)
            return;
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : items) {
            if (geo.box != null) {
                minX = Math.min(minX, geo.box.minimums.getCoordinate().getX());
                minY = Math.min(minY, geo.box.minimums.getCoordinate().getY());
                minZ = Math.min(minZ, geo.box.minimums.getCoordinate().getZ());
                maxX = Math.max(maxX, geo.box.maximums.getCoordinate().getX());
                maxY = Math.max(maxY, geo.box.maximums.getCoordinate().getY());
                maxZ = Math.max(maxZ, geo.box.maximums.getCoordinate().getZ());
            }
        }
        box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (box == null)
            return findGeoIntersectionsHelper(ray);
        List<GeoPoint> geometries1 = new LinkedList<>();
        if (intersectable.isIntersectingBoundingBox(ray)) {
            if (isLeaf()) {
                // Perform intersection test with individual geometries at the leaf node
                return findGeoIntersectionsHelper(ray);
            } else {
                // Intersect with left and right child nodes recursively
                List<GeoPoint> leftIntersect = left.findGeoIntersections(ray);
                List<GeoPoint> rightIntersect = right.findGeoIntersections(ray);

                if (leftIntersect != null)
                    geometries1.addAll(leftIntersect);
                if (rightIntersect != null)
                    geometries1.addAll(rightIntersect);
            }
        }
        if (geometries1.size() == 0)
            return null;

        // If the ray does not intersect the bounding box of the node, return false
        return geometries1;
    }

}
