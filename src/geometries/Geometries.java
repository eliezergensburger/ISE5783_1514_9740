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

        List<Point> it = new LinkedList<>();
        for(Intersectable element : this.items){
            List<Point> x =element.findIntersections(ray);
            if(x!= null ){
                it.addAll(x);
            }

        }
        return it.size() == 0 ? null :  it ;
    }
}
