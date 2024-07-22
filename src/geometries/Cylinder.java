package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * Represents a cylinder in 3D space, extending from a base {@link Tube} with a certain height.
 */
public class Cylinder extends Tube {


    private final Double height;

    /**
     * Constructs a Cylinder object with a given height, axis {@link Ray}, and radius.
     *
     * @param h    The height of the cylinder.
     * @param axis The axis of the cylinder, represented by a {@link Ray}.
     * @param r    The radius of the cylinder.
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
        if (p.equals(axis.getHead()) || p.subtract(axis.getHead()).dotProduct(axis.getDirection()) == 0)
            return axis.getDirection();
        else {
            return super.getNormal(p);
        }

    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new LinkedList<>();

        // Find intersections with the side surface of the cylinder
        List<GeoPoint> sideIntersections = findSideIntersections(ray,maxDistance);
        if (sideIntersections != null) {
            intersections.addAll(sideIntersections);
        }

        // There are at most 2 cut points so there is no point in continuing to play
        if (intersections.size() == 2) {
            return List.of( intersections.get(0), intersections.get(1));
        }


        // Find intersections with the bottom and top bases of the cylinder
        List<GeoPoint> bottomBaseIntersections = findBaseIntersections(ray, axis.getHead(),maxDistance);
        if (bottomBaseIntersections != null) {
            intersections.addAll(bottomBaseIntersections);
        }

        List<GeoPoint> topBaseIntersections = findBaseIntersections(ray, axis.getHead().add(axis.getDirection().scale(height)),maxDistance);
        if (topBaseIntersections != null) {
            intersections.addAll(topBaseIntersections);
        }

        if (intersections.isEmpty()) {
            return null;
        }

        // Sort the intersections based on the distance from the ray head
        intersections.sort((p1, p2) -> Double.compare(ray.getHead().distance(p1.point), ray.getHead().distance(p2.point)));
        if (intersections.size() == 1)
            return List.of(intersections.get(0));
        return List.of( intersections.get(0), intersections.get(1));
    }


    private List<GeoPoint> findSideIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections;
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Point p1 = axis.getHead();
        Vector vAxis = axis.getDirection();

        Vector deltaP = p0.subtract(p1);

        // Check if v and vAxis are parallel
        if (v.isParallel(vAxis)) {
            return null; // if the ray is parallel to the axis of the cylinder, there are no intersections on the side surface
        }

        Vector vCrossVA = v.crossProduct(vAxis);
        Vector deltaPCrossVA = deltaP.crossProduct(vAxis);

        // Calculate the solutions of the following quadratic equation and find the values of t1,t2
        double a = vCrossVA.lengthSquared(); // represents the area of the parallelogram formed by the vectors v and vAxis
        double b = 2 * vCrossVA.dotProduct(deltaPCrossVA);
        // represents the relationship between the beam and its distance from the cylinder axis
        double c = deltaPCrossVA.lengthSquared() - (radius * radius * vAxis.lengthSquared());
        // represents the distance of the beam from the axis of the cylinder minus the radius of the cylinder squared
        double discriminant = alignZero(b * b - 4 * a * c);

        if (isZero(discriminant)) {
            double t = alignZero(-b / (2 * a));
            if(t>maxDistance)
                return null;
            Point p = ray.getPoint(t);
            // The scalar product returns the charge of the vector P-P1 on the axis direction of the cylinder,
            // i.e. the distance of the point p from the top of the roll
            double z = alignZero(p.subtract(p1).dotProduct(vAxis));
            if (z > 0 && z < height) {
                // Check if the ray started inside the cylinder
                if (p0.distanceSquared(p1.add(vAxis.scale(z))) < radius * radius) {
                    intersections=new ArrayList<>();
                    intersections.add(new GeoPoint(this,p));
                    return intersections;// Ray started inside the cylinder, return the intersection point
                } else {
                    return null; // Ray started outside the cylinder, this is a tangential intersection
                }
            }
            return null;
        } else if (discriminant < 0) {
            return null;
        }
        intersections=new ArrayList<>();
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = alignZero((-b - sqrtDiscriminant) / (2 * a));
        double t2 = alignZero((-b + sqrtDiscriminant) / (2 * a));
        // t1 and t2 expressions that express the distances from the head point of the beam (p0) along the direction
        // of the beam (v) to the intersection points
        if(alignZero(t1-maxDistance)<=0)
          addValidIntersection(intersections, ray, t1, p1, vAxis);
        if(alignZero(t2-maxDistance)<=0)
         addValidIntersection(intersections, ray, t2, p1, vAxis);

        return intersections.isEmpty() ? null : intersections;
    }

    private void addValidIntersection(List<GeoPoint> intersections, Ray ray, double t, Point p1, Vector vAxis) {
        if (t > 0) {
            Point p = ray.getPoint(t);
            double z = alignZero(p.subtract(p1).dotProduct(vAxis));
            // The scalar product returns the charge of the vector P-P1 on the axis direction of the cylinder,
            // i.e. the distance of the point p from the top of the roll
            if (z > 0 && z < height) {
                intersections.add(new GeoPoint(this,p));
            }
        }
    }

    private List<GeoPoint> findBaseIntersections(Ray ray, Point center, double maxDistance) {
        Vector vAxis = axis.getDirection();
        Vector v = ray.getDirection();
        if (vAxis.isParallel(v))
            if (ray.getHead().getX() >= (center.getX() + radius))
                return null;
        Plane basePlane = new Plane(center, vAxis);

        List<GeoPoint> baseIntersections = basePlane.findGeoIntersections(ray,maxDistance);
        if (baseIntersections == null) {
            return null;
        }

        List<GeoPoint> intersections = new LinkedList<>();
        for (GeoPoint p : baseIntersections) {
            if (alignZero(p.point.distance(center) - radius) <= 0) {
                intersections.add(new GeoPoint(this,p.point));
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }

}



