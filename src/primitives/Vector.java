package primitives;

/**
 *The class represents a vector which is a fundamental object in 3D geometry, with direction and size,
 * The class inherits from the Point class
 */
public class Vector extends Point {

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     * @throws IllegalArgumentException if the zero vector is inserted.
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("The zero vector must not be inserted");
    }

    /**
     * Constructs a new vector from a Double3 object.
     *
     * @param obj The Double3 object.
     * @throws IllegalArgumentException if the zero vector is inserted.
     */
    public Vector(Double3 obj) {
        super(obj);
        if (obj.equals(Double3.ZERO))
            throw new IllegalArgumentException("The zero vector must not be inserted");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && super.equals(other);
    }

    @Override
    public String toString() {
        return (super.toString());
    }

    /**
     *This method adds another vector to this vector.
     *
     * @param vecToAdd The vector to add.
     * @return The resulting vector.
     */
    public Vector add(Vector vecToAdd) {
        if(Double3.ZERO.equals(super.add(vecToAdd).xyz))
            throw new IllegalArgumentException("ERROR:cant add vector with its negative vector");
        return new Vector(super.add(vecToAdd).xyz);
    }

    /**
     *This method scales this vector by a scalar.
     *
     * @param num The scalar to scale by.
     * @return The scaled vector.
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * This method calculates the dot product of this vector and another vector.
     *
     * @param vec The other vector.
     * @return The dot product.
     */
    public double dotProduct(Vector vec) {
        return (xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3);
    }

    /**
     * This method calculates the cross product of this vector and another vector.
     *
     * @param vec The other vector.
     * @return The cross product vector.
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2,
                xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3,
                xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1);
    }

    /**
     * This method calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    public double lengthSquared() {
        return super.distanceSquared(new Point(0d, 0d, 0d));
    }

    /**
     * This method calculates the length of this vector.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * This method normalizes this vector.
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        return scale(1 / length());
    }

}
