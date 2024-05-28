package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This is a class that represents a plane. It implements the geometry class
 */
public class Plane implements Geometry {
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
        if(p1==p2||p1==p3||p2==p3)
            throw new IllegalArgumentException("ERROR:There is coalesce points");
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        Vector n = v1.crossProduct(v2);
        if(n.equals(Vector.ZERO))
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
}