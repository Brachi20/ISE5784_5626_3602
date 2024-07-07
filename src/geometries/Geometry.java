package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * This interface represents any geometric objects
 */
public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color of the geometry.
     * @return The geometry.
     */

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Gets the normal vector to the geometry at a given point.
     *
     * @param p The point on the geometry.
     * @return The normal vector to the geometry at the given point.
     */
    public abstract Vector getNormal(Point p);

    /**
     * Gets the material of the geometry.
     *
     * @return The material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry.
     *
     * @param material The material of the geometry.
     * @return The geometry.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
