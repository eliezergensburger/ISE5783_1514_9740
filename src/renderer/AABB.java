package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Polygon;
import geometries.Sphere;
import primitives.Point;

import java.util.List;

class AABB {
    double minX, minY, minZ;
    double maxX, maxY, maxZ;

    public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    public AABB(Intersectable intersectable) {
        if (intersectable instanceof Sphere sphere){
            double x  = sphere.getCenter().getCoordinate().getX();
            double y  = sphere.getCenter().getCoordinate().getY();
            double z  = sphere.getCenter().getCoordinate().getZ();
            double radius = sphere.getRadius();
            this.minX = x - radius;
            this.minY = y - radius;
            this.minZ = z - radius;
            this.maxX = x + radius;
            this.maxY = y + radius;
            this.maxZ = z + radius;
        }
        else if(intersectable instanceof Polygon) {
            Polygon polygon = (Polygon) intersectable;
            List<Point> vertices = polygon.getVertices();
            this.minX = Double.POSITIVE_INFINITY;
            this.minY = Double.POSITIVE_INFINITY;
            this.minZ = Double.POSITIVE_INFINITY;
            this.maxX = Double.NEGATIVE_INFINITY;
            this.maxY = Double.NEGATIVE_INFINITY;
            this.maxZ = Double.NEGATIVE_INFINITY;
            for (Point ver : vertices) {
                double x = ver.getCoordinate().getX();
                double y = ver.getCoordinate().getY();
                double z = ver.getCoordinate().getZ();
                if(x > this.maxX)
                    maxX = x;
                if(y > this.maxY)
                    maxY = y;
                if(z > this.maxZ)
                    maxZ = z;
                if(x < this.minX)
                    minX = x;
                if(y < this.minY)
                    minY = y;
                if(z < this.minZ)
                    minZ = z;
            }
        }
    }

    static Geometries intersectGeometries(BVHNode node, double originX, double originY, double originZ,
                                       double directionX, double directionY, double directionZ) {
        Geometries geometries = new Geometries();
        // Iterate over the geometries associated with the leaf node and perform intersection tests
        for (Intersectable geometry : node.geometries) {
            AABB geo = new AABB(geometry);
            if (geo.intersectRay(originX, originY, originZ, directionX, directionY, directionZ)) {
                geometries.add(geometry);
            }
        }

        // If no intersection with any geometries, return false
        return geometries;
    }


    public boolean intersectRay(double originX, double originY, double originZ,
                                double directionX, double directionY, double directionZ) {

        double tmin = (minX - originX) / directionX;
        double tmax = (maxX - originX) / directionX;

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        double tymin = (minY - originY) / directionY;
        double tymax = (maxY - originY) / directionY;

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if (tmin > tymax || tymin > tmax) {
            return false;
        }

        if (tymin > tmin) {
            tmin = tymin;
        }

        if (tymax < tmax) {
            tmax = tymax;
        }

        double tzmin = (minZ - originZ) / directionZ;
        double tzmax = (maxZ - originZ) / directionZ;

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if (tmin > tzmax || tzmin > tmax) {
            return false;
        }

        return true;
    }
}



