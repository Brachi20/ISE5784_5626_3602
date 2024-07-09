package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This is a class that represents a plane. It implements the geometry class
 */
public class Plane extends Geometry {


    private final Point q;
    private final Vector normal;

    /**
     * Constructs a new plane passing through three points.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        if (p1 == p2 || p1 == p3 || p2 == p3)
            throw new IllegalArgumentException("ERROR:There is coalesce points");
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        Vector n = v1.crossProduct(v2);
        if (n.equals(Vector.ZERO))
            throw new IllegalArgumentException("ERROR:the points are on the same line");
        normal = n.normalize();
        q = p1; // Assigning one of the points to q, assuming it's a point on the plane
    }

    /**
     * Constructs a new plane passing through a point with a given normal vector.
     *
     * @param p      A point on the plane.
     * @param normal The normal vector of the plane.
     */
    public Plane(Point p, Vector normal) {
        q = p;
        this.normal = normal.normalize();
    }

    /**
     * get method of normal filed
     * This method implements the method in a geometric interface
     *
     * @return The normal vector.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the normal vector of the plane at a given point (for consistency with the interface).
     * * Returns the normal vector of the plane (this method does exactly what the getNormal method
     * does, and we implemented the first one as part of the commitment to implement an interface)
     *
     * @param p The point (ignored).
     * @return The normal vector.
     */
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Finds the intersection points between the given ray and the plane.
     *
     * @param ray The ray to find intersections with.
     * @return A list containing the intersection point, or null if there is no intersection.
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //calculate the t value for the distance between the head of the ray and the point of intersection
        double nv = ray.getDirection().dotProduct(normal);
        //if the ray is parallel to the plane
        if (isZero(nv))
            return null;

        //if the head of the ray is on the plane
        if (this.q.equals(ray.getHead()))
            return null;
        double t = normal.dotProduct(this.q.subtract(ray.getHead())) / nv;
        //if the ray is on the plane
        if (alignZero(t) == 0)
            return null;
        // if the ray is on the other direction of the plane
        if (t < 0)
            return null;
        //calculate the point of intersection
        Point p = ray.getPoint(t);
        return List.of(new GeoPoint(this, p));
    }
}