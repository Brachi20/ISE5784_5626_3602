package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in 3D space.
 */
public abstract class Sphere extends RadialGeometry {

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
}