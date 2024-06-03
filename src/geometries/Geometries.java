package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> geometries=new LinkedList<Intersectable>();

    public Geometries(){};

    public Geometries(List<Intersectable> g){
        add(g);
    }
    public void add(List<Intersectable> g){
        if(!geometries.isEmpty())
            geometries.addAll(g);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
