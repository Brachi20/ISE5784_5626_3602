package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CameraIntegrationTest {
    final String WRONG_NUMBER = "Wrong number of points";

    private final Camera.Builder cameraBuilder = Camera.getBuilder().setVpDistance(1);

    private Camera camera13X3= cameraBuilder
            .setVpSize(3,3)
            .build();
      private Camera camera14X4 = cameraBuilder
            .setVpSize(4,4)
            .build();
    private Camera camera23X3 = cameraBuilder
            .setLocation(new Point(0,0,0.5))
            .setVpSize(3,3)
            .build();
    private Camera camera24X4 =  cameraBuilder
            .setLocation(new Point(0,0,0.5))
            .setVpSize(4,4)
            .build();

    private int intersectionsPoints(Geometry g, Camera c,int nX,int nY) {
        int count = 0;
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Ray oneRay = c.constructRay(nX, nY, j, i);
                List<Point> list = g.findIntersections(oneRay);
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
        assertEquals(2, intersectionsPoints(s1, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(18, intersectionsPoints(s1, camera14X4,4,4), WRONG_NUMBER);

        //TC02: 18 intersections
        assertEquals(18, intersectionsPoints(s2, camera23X3,3,3), WRONG_NUMBER);
        assertEquals(18, intersectionsPoints(s2, camera24X4,4,4), WRONG_NUMBER);

        //TC03:for 3X3 view plane 10 intersections for 4X4 view plane 18 intersections
        assertEquals(10, intersectionsPoints(s3, camera23X3,3,3), WRONG_NUMBER);
        assertEquals(18, intersectionsPoints(s3, camera24X4,4,4), WRONG_NUMBER);

        //TC04: for 3X3 view plane 9 intersections for 4X4 view plane 18 intersections
        assertEquals(9, intersectionsPoints(s4, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(18, intersectionsPoints(s4, camera14X4,4,4), WRONG_NUMBER);

        //TC05: for 3X3 view plane 0 intersections for 4X4 view plane 0 intersections
        assertEquals(0, intersectionsPoints(s5, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(0, intersectionsPoints(s5, camera14X4,4,4), WRONG_NUMBER);
    }
@Test
    void triangleIntegrationTest() {
        Triangle t1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1,-1,-2));
        Triangle t2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1,-1,-2));

        //TC01: for 3X3 view plane 1 intersections for 4X4 view plane 1 intersections
        assertEquals(1, intersectionsPoints(t1, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(1, intersectionsPoints(t1, camera14X4,4,4), WRONG_NUMBER);

        //TC02: for 3X3 view plane 2 intersections for 4X4 view plane 2 intersections
        assertEquals(2, intersectionsPoints(t2, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(2, intersectionsPoints(t2, camera14X4,4,4), WRONG_NUMBER);
    }

@Test
    void planeIntegrationTest() {
        Plane p1 = new Plane(new Point(0, 0, -2), new Point(0, 1, -2), new Point(2, 1, -2));
        Plane p2 = new Plane(new Point(0, 0, -2), new Vector(0, -1, -2));
        Plane p3 = new Plane(new Point(0, 0, -3), new Vector(0, -1, -1));

        //TC01: for 3X3 view plane 9 intersections for 4X4 view plane 9 intersections
        assertEquals(9, intersectionsPoints(p1, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(9, intersectionsPoints(p1, camera14X4,4,4), WRONG_NUMBER);

        //TC02: for 3X3 view plane 9 intersections for 4X4 view plane 6 intersections
        assertEquals(9, intersectionsPoints(p2, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(6, intersectionsPoints(p2, camera14X4,4,4), WRONG_NUMBER);

        //TC03:for 3X3 view plane 6 intersections for 4X4 view plane 6 intersections
        assertEquals(6, intersectionsPoints(p3, camera13X3,3,3), WRONG_NUMBER);
        assertEquals(6, intersectionsPoints(p3, camera14X4,4,4), WRONG_NUMBER);
    }
}
