package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 *
 */
public abstract class Intersectable {

    /**
     * A class that represents a point in the 3D space.
     */
    public static class GeoPoint {

        /**
         * The geometry associated with the point.
         */
        public Geometry geometry;

        /**
         * The point associated with the geometry.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint object with the given geometry and point.
         *
         * @param geometry The geometry associated with the point.
         * @param point    The point associated with the geometry.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint other)
                    && this.geometry.equals(other.geometry)
                    && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }


    /**
     *
     * @param ray
     * @return
     */
    public final List<Point> findIntersections(Ray ray){
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    };


    public final List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return findGeoIntersectionsHelper(ray);
    }

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        return null;
    }

}
