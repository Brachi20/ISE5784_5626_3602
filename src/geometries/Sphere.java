package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a Sphere object with a given center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, Double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead(); // the head of the ray
        Vector v = ray.getDirection(); // the direction of the ray

        Vector x = this.center.subtract(p0); // the vector from the head of the ray to the center of the sphere
        // Check if the ray head is on the sphere
        if (alignZero(x.length() - this.radius) == 0) {
            // If the direction of the ray is pointing outwards from the sphere
            if (alignZero(v.dotProduct(x)) >= 0) {
                return null; // The ray is directed outwards from the sphere
            }
        }

        if (ray.getHead().equals(this.center)) // if the head of the ray is the center of the sphere
            if(this.radius<=maxDistance)
              return List.of(new GeoPoint(this, p0.add(v.scale(this.radius)))); // return the point of intersection
        Vector u = this.center.subtract(p0); // the vector from the head of the ray to the center of the sphere
        double tm = v.dotProduct(u); // the projection of u on the ray
        double d = Math.sqrt(u.lengthSquared() - tm * tm); // the distance between the center of the sphere and the ray
        if (d >= this.radius) // if the distance is bigger or equal to the radius of the sphere
            return null;
        double th = Math.sqrt(this.radius * this.radius - d * d);// the distance between the point of intersection
        // and the projection of the center of the sphere on the ray
        double t1 = tm - th; // the distance between the head of the ray and the point of intersection
        double t2 =tm + th; // the distance between the head of the ray and the point of intersection
        if (t1 > 0 && t2 > 0) { // if the ray is in the direction of the sphere
            if (t1 <= maxDistance && t2 <= maxDistance)
                return List.of(new GeoPoint(this, ray.getPoint(t1)),
                        new GeoPoint(this, ray.getPoint(t2))); // return the points of intersection
            if (t1 <= maxDistance)
                return List.of(new GeoPoint(this, ray.getPoint(t1))); // return the point of intersection
            if (t2 <= maxDistance)
                return List.of(new GeoPoint(this, ray.getPoint(t2))); // return the point of intersection
        }
        if (t1 > 0 && t1<= maxDistance) // if the ray is in the direction of the sphere
            return List.of(new GeoPoint(this, ray.getPoint(t1))); // return the point of intersection
        if (t2 > 0 && t2<= maxDistance) // if the ray is in the direction of the sphere
            return List.of(new GeoPoint(this, ray.getPoint(t2))); // return the point of intersection
        return null;
    }

    /**
     * Computes the normal vector to the sphere at a given point.
     * Since the sphere is a curved surface, the normal vector at any point on its surface
     * is a vector pointing from the center of the sphere to the given point.
     *
     * @return The normal vector to the sphere.
     */
    public Vector getNormal() {
        return null; // Not implemented in abstract class, should be implemented in subclasses
    }

    public Vector getNormal(Point p) {
        if (p.equals(center))
            throw new IllegalArgumentException("The point is the center of the sphere");
        return p.subtract(center).normalize();
        // Not implemented in abstract class, should be implemented in subclasses
    }
}