package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    private List<Intersectable> items ;

    public Geometries() {
        items = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries){
        items =List.of(geometries);
    }

    public void add(Intersectable... geometries){
        items.addAll(List.of(geometries));
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
}
