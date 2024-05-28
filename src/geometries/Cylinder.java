package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in 3D space, extending from a base {@link Tube} with a certain height.
 */
public class Cylinder extends Tube {

    private final Double height;

    /**
     * Constructs a Cylinder object with a given height, axis {@link Ray}, and radius.
     *
     * @param h The height of the cylinder.
     * @param axis The axis of the cylinder, represented by a {@link Ray}.
     * @param r The radius of the cylinder.
     */
    public Cylinder(Double h, Ray axis, double r) {
        super(axis, r);
        height = h;
    }

    /**
     * Computes the normal vector to the cylinder at a given point.
     * Since the cylinder is an infinite surface, it does not have a normal vector at any specific point,
     * thus this method returns null.
     *
     * @param p The point on the surface of the cylinder.
     * @return Returns null since the cylinder is an infinite surface.
     */
    public Vector getNormal(Point p) {
        if(p.equals(axis.getHead()) || p.subtract(axis.getHead()).dotProduct(axis.getDirection())==0)
            return axis.getDirection();
        else
        {
            return super.getNormal(p);
        }

    }
}