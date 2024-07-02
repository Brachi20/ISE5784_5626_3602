package primitives;

import java.util.List;
import java.util.ListIterator;

import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;

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

    /**
     * get method for head point of Ray
     * @return point of ray's head
     */
    public Point getHead(){
        return head;
    }

    /**
     * get method for vector direction of Ray
     * @return vector of ray's direction
     */
    public Vector getDirection(){
        return direction;
    }

    /**
     * get point on the ray at a distance t from the head
     * @param t distance from the head
     * @return point on the ray at a distance t from the head
     */
    public Point getPoint(double t){
        if(isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * find the closest point to the head of the ray from a list of points
     * @param points list of points
     * @return the closest point to the head of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        if (points.isEmpty()) {
            return null;
        }
        return findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null,p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null) {
            return null;
        }
        ListIterator<GeoPoint> iter = points.listIterator();
        GeoPoint closest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        while (iter.hasNext()) {
            GeoPoint current = iter.next();
            double currentDistance = head.distance(current.point);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closest = current;
            }
        }
        return closest;
    }
}
