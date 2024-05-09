package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This interface represents any geometric objects
 */
public interface Geometry {

    /**
     * Gets the normal vector to the geometry at a given point.
     *
     * @param p The point on the geometry.
     * @return The normal vector to the geometry at the given point.
     */
    public Vector getNormal(Point p);
}
