package primitives;
/**
 * Class Ray is the basic class representing a fundamental object in 3D geometry, the group of points on a straight line that are on one side of a given point
 * called the head of the beam, defined by fields of point and direction
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructs a new ray with the given head and direction.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        // Normalize the direction vector
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
