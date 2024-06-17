package Renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IntegrationTest {
    final String WRONG_NUMBER = "Wrong number of points";

    private final Camera.Builder cameraBuilder = Camera.getBuilder().setVpDistance(1).setVpSize(3, 3);
    Camera camera1 = cameraBuilder.build();
    Camera camera2 = cameraBuilder.setLocation(new Point(0, 0, 0.5)).build();

    private int intersectionsPoints(Geometry g, Camera c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                List<Point> list = g.findIntersections(c.constructRay(3, 3, j, i));
                if (list != null) {
                    count += list.size();
                }
            }
        }
        return count;
    }

 @Test
    void sphereIntegrationTest() {
        Sphere s1 = new Sphere(new Point(0, 0, -3), 1d);
        Sphere s2 = new Sphere(new Point(0, 0, -2.5), 2.5d);
        Sphere s3 = new Sphere(new Point(0, 0, -2), 2d);
        Sphere s4 = new Sphere(new Point(0, 0, -1), 4d);
        Sphere s5 = new Sphere(new Point(0, 0, 1), 0.5);

        //TC01: 2 intersections
        assertEquals(2, intersectionsPoints(s1, camera1), WRONG_NUMBER);

        //TC02: 18 intersections
        assertEquals(18, intersectionsPoints(s2, camera2), WRONG_NUMBER);

        //TC03: 10 intersections
        assertEquals(10, intersectionsPoints(s3, camera2), WRONG_NUMBER);

        //TC04: 9 intersections
        assertEquals(9, intersectionsPoints(s4, camera1), WRONG_NUMBER);

        //TC05: 0 intersections
        assertEquals(0, intersectionsPoints(s5, camera1), WRONG_NUMBER);
    }
@Test
    void triangleIntegrationTest() {
        Triangle t1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1,-1,-2));
        Triangle t2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1,-1,-2));

        //TC01: 1 intersection
        assertEquals(1, intersectionsPoints(t1, camera1), WRONG_NUMBER);

        //TC02: 2 intersections
        assertEquals(2, intersectionsPoints(t2, camera1), WRONG_NUMBER);
    }

@Test
    void planeIntegrationTest() {
        Plane p1 = new Plane(new Point(0, 0, -2), new Point(0, 1, -2), new Point(2, 1, -2));
        Plane p2 = new Plane(new Point(0, 0, -2), new Vector(0, -1, -2));
        Plane p3 = new Plane(new Point(0, 0, -3), new Vector(0, -1, -1));

        //TC01: 9 intersections
        assertEquals(9, intersectionsPoints(p1, camera1), WRONG_NUMBER);

        //TC02: 9 intersections
        assertEquals(9, intersectionsPoints(p2, camera1), WRONG_NUMBER);

        //TC03: 6 intersections
        assertEquals(6, intersectionsPoints(p3, camera1), WRONG_NUMBER);
    }
}
