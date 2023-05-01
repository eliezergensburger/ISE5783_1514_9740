package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

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
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Boolean flag = false;
        for(Intersectable element : this.items){
            if(element.findIntersections(ray) != null){
                flag = true;
            }
        }
        if(!flag)
            return null;

        List<Point> it = new LinkedList<>();
        for(Intersectable element : this.items) {
            List<Point> x = element.findIntersections(ray);
            if (x != null)
                it.addAll(x);
        }

        return it ;
    }
}
