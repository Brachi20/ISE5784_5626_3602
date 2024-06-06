package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> geometries=new LinkedList<Intersectable>();

    public Geometries(){};

    public Geometries(Intersectable... geometries){
        add(geometries);
    }
    public void add(Intersectable... geometries){
        if(geometries!= null)
            this.geometries.addAll(List.of(geometries));
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
