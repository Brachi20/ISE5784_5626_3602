package geometries;

/**
 * This is a class that implements the Geometric interface and has only a radius field
 */
public abstract class RadialGeometry extends Geometry {
    //List<Point> findIntersections(Ray ray);
    final protected double radius;

    /**
     * Constructs a new radial geometry object with the given radius.
     *
     * @param r The radius of the radial geometry object.
     */
    public RadialGeometry(double r) {
        radius = r;
    }


}
