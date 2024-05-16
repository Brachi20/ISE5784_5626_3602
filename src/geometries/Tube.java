//Hen Elkayam 214243602 Henelkayam99@gmail.com
//Brachi Tarkieltaub 325925626 tarkielt@g.jct.ac.il

package geometries;

import primitives.Vector;
import primitives.Ray;

/**
 * Represents an abstract tube in 3D space.
 * A tube is defined by its axis, a line in 3D space, and a radius.
 */
public abstract class  Tube extends RadialGeometry {

    /**
     * The axis of the tube, represented by a {@link Ray}.
     */
    protected final Ray axis;

    /**
     * Constructs a Tube object with a given axis and radius.
     *
     * @param axis The axis of the tube, represented by a {@link Ray}.
     * @param radius The radius of the tube.
     */
    public Tube(Ray axis, Double radius) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Computes the normal vector to the tube at a given point.
     * Since the tube is a curved surface, the normal vector at any point on its surface
     * is perpendicular to both the direction of the tube's axis and the direction from
     * the axis to the given point.
     *
     * @return The normal vector to the tube.
     */
    public Vector getNormal() {
        return null; // Not implemented in abstract class, should be implemented in subclasses
    }
}
