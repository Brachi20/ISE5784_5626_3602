package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * This is a class that defines a triangle (a geometric body)
 * it implements a polygon since a triangle is a polygon with 3 vertices
 */
public class Triangle extends Polygon{

        public Triangle(Point p1, Point p2, Point p3) {
            super(new Point[]{p1, p2, p3});
        }

    public List<Point> findIntersections(Ray ray){
        if(plane.findIntersections(ray)==null)
            return null;
        Point start=ray.getHead();
        Vector direction=ray.getDirection();
        Vector v1=vertices.get(0).subtract(start);
        Vector v2=vertices.get(1).subtract(start);
        Vector v3=vertices.get(2).subtract(start);
        Vector n1=v1.crossProduct(v2).normalize();
        Vector n2=v2.crossProduct(v3).normalize();
        Vector n3=v3.crossProduct(v1).normalize();

        double t1=direction.dotProduct(n1);
        double t2=direction.dotProduct(n2);
        double t3=direction.dotProduct(n3);

        if(t1>0&&t2>0&&t3>0||t1<0&&t2<0&&t3<0)
            return plane.findIntersections(ray);
        return null;
    }
}
