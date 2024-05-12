package primitives;

/**
 * Represents a point in 3D space.
 */
public class Point {
    /** The coordinates of the point */
    protected final Double3 xyz;

    /** Represents the origin (0, 0, 0) */
    public static final Point ZERO = new Point(0d, 0d, 0d);

    /**
     * Constructs a new Point object with the specified coordinates.
     * @param d1 The X coordinate
     * @param d2 The Y coordinate
     * @param d3 The Z coordinate
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a new Point object from a Double3 object.
     * @param xyz The Double3 object representing the coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Computes the vector from this point to another point.
     * @param p The other point
     * @return The vector from this point to the other point
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     * @param v The vector to add
     * @return The new point after adding the vector
     */
    public Point add(Vector v) {
        return new Point(this.xyz.add(v.xyz));
    }

    /**
     * Computes the square of the distance between this point and another point.
     * @param p The other point
     * @return The square of the distance between this point and the other point
     */
    public double distanceSquared(Point p) {
        return ((this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) +
                (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2) +
                (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3));
    }

    /**
     * Computes the distance between this point and another point.
     * @param p The other point
     * @return The distance between this point and the other point
     */
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

}
