package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Comparator;
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
        List<Point> intersections=null;
        for(Intersectable geo:geometries){
            if(geo.findIntersections(ray)!=null){
                if(intersections==null)
                    intersections=new LinkedList<Point>();
                intersections.addAll(geo.findIntersections(ray));
            }
        }
        if(intersections!=null)
            return intersections.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead()))).toList();
        else return null;
    }

}
