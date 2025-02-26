package geometries;

import primitives.Ray;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    private List<Intersectable> geometries = new LinkedList<Intersectable>();

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        if (geometries != null)
            this.geometries.addAll(List.of(geometries));
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geo : geometries) {
            var geoPoints = geo.findGeoIntersections(ray,maxDistance);
            if (geoPoints != null) {
                if (intersections == null)
                    intersections = new LinkedList<GeoPoint>();
                intersections.addAll(geoPoints);
            }
        }
        if (intersections != null)
            return intersections.stream().sorted(Comparator.comparingDouble(p -> p.point.distance(ray.getHead()))).toList();
        else return null;
    }

}
