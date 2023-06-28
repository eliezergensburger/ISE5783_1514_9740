package renderer;

import geometries.Geometries;
import geometries.Intersectable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static renderer.AABB.intersectGeometries;

class BVHNode {
    AABB boundingBox;
    BVHNode left;
    BVHNode right;
    // Other properties or references to geometries
    List<Intersectable> geometries;

    static private int longestAxis;

    static public Map<Intersectable, AABB> map = new HashMap<>();

    public BVHNode(AABB boundingBox, BVHNode left, BVHNode right, List<Intersectable> geometries) {
        this.boundingBox = boundingBox;
        this.left = left;
        this.right = right;
        this.geometries = geometries;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public static BVHNode buildBVH(List<Intersectable> geometries) {
        if (geometries.isEmpty()) {
            return null;
        }

        for (Intersectable inter: geometries) {
            map.put(inter, new AABB(inter));
        }
        return recursiveBuildBVH(geometries, 0, geometries.size() - 1);
    }

    private static BVHNode recursiveBuildBVH(List<Intersectable> geometries, int start, int end) {
        if (start == end) {
            // Create a leaf node
            return new BVHNode(map.get(geometries.get(0)), null, null, geometries);
        }

        // Find the axis with the longest extent
        int longestAxis = findLongestAxis(geometries, 0, end - start);

        // Sort geometries based on the longest axis
        geometries.sort(Comparator.comparingDouble(a -> getCentroid(new AABB(a), longestAxis)));

        int mid = (start + end)/ 2;

        // Recursively build left and right subtrees
        BVHNode left = recursiveBuildBVH(geometries.subList(0, mid - start + 1), start, mid);
        BVHNode right = recursiveBuildBVH(geometries.subList(mid - start + 1, end - start + 1), mid + 1, end);

        // Calculate the bounding box for the current node
        AABB boundingBox = calculateBoundingBox(geometries, 0, end - start);

        // Create an internal node
        return new BVHNode(boundingBox, left, right, geometries);
    }

    private static int findLongestAxis(List<Intersectable> geometries, int start, int end) {
        double maxSAH = Double.POSITIVE_INFINITY;
        int bestAxis = 0;

        for (int axis = 0; axis < 3; axis++) {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;

            for (int i = start; i <= end; i++) {
                double centroid = getCentroid(map.get(geometries.get(i)), axis);
                min = Math.min(min, centroid);
                max = Math.max(max, centroid);
            }

            double extent = max - min;
            double sah = extent * (end - start + 1);

            if (sah < maxSAH) {
                maxSAH = sah;
                bestAxis = axis;
            }
        }

        return bestAxis;
    }

    private static double getCentroid(AABB geometry, int axis) {
        switch (axis) {
            case 0:
                return (geometry.minX + geometry.maxX) / 2.0;
            case 1:
                return (geometry.minY + geometry.maxY) / 2.0;
            case 2:
                return (geometry.minZ + geometry.maxZ) / 2.0;
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    private static AABB calculateBoundingBox(List<Intersectable> geometries, int start, int end) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (int i = start; i <= end; i++) {
            AABB geometry = map.get(geometries.get(i));
            minX = Math.min(minX, geometry.minX);
            minY = Math.min(minY, geometry.minY);
            minZ = Math.min(minZ, geometry.minZ);
            maxX = Math.max(maxX, geometry.maxX);
            maxY = Math.max(maxY, geometry.maxY);
            maxZ = Math.max(maxZ, geometry.maxZ);
        }

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Geometries intersectBVH(BVHNode node, double originX, double originY, double originZ,
                                          double directionX, double directionY, double directionZ) {
        Geometries geometries1 = new Geometries();
        if (node.boundingBox.intersectRay(originX, originY, originZ, directionX, directionY, directionZ)) {
            if (node.isLeaf()) {
                // Perform intersection test with individual geometries at the leaf node
                return intersectGeometries(node, originX, originY, originZ, directionX, directionY, directionZ);
            } else {
                // Intersect with left and right child nodes recursively
                Geometries leftIntersect = intersectBVH(node.left, originX, originY, originZ, directionX, directionY, directionZ);
                Geometries rightIntersect = intersectBVH(node.right, originX, originY, originZ, directionX, directionY, directionZ);

                for (Intersectable inter:leftIntersect.getItems()) {
                    geometries1.add(inter);
                }
                for (Intersectable inter:rightIntersect.getItems()) {
                    geometries1.add(inter);
                }
            }
        }

        // If the ray does not intersect the bounding box of the node, return false
        return geometries1;
    }

}
