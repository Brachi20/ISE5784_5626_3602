package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    private final Point p100 = new Point(1, 0, 0);
    private final Point p050 = new Point(0, 5, 0);
    private final Point p120 = new Point(1, 2, 0);
    private final Plane p = new Plane(p050, p100, p120);
    private final Point p1 = new Point(-1, 10, 0);

    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * test method for {@link geometries.Plane #Plane(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some point
        assertNotNull(p, "Failed constructing a plane");

        // =============== Boundary Values Tests ==================

        // TC11: A test for building a plan with coalesce points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p050, p050, p100),
                "ERROR:coalesce point in plane, did not throw an exception");

        // TC12: A test for building a plan with 3 point on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p050, p100, p1),
                "ERROR: the points are on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: test calculate normal
        assertTrue(p.getNormal().normalize().equals(v001)
                        || p.getNormal().normalize().equals(new Vector(0, 0, -1)),
                "ERROR:normal of plane is not correct");
    }


    //veribles for the testFindIntersections and testFindGeoIntersectionsHelper
    private final Plane p2 = new Plane(p120, v001);

    private final Point gp1 = new Point(0.5, 0.5, 0);

    private final Point p01 = new Point(0, 0, -1);
    private final Point p02 = new Point(1, 1, 1);
    private final Point p03 = new Point(0, 5, 1);
    private final Point p04 = new Point(1, 0, -1);
    private final Point p05 = new Point(1, 0, 1);

    private final Vector v1 = new Vector(1, 1, 2);

    private final Ray r1 = new Ray(p01, v1);
    private final Ray r2 = new Ray(p02, v1);
    private final Ray r3 = new Ray(p050, new Vector(1, 1, 0));
    private final Ray r4 = new Ray(p03, new Vector(0, -10, 0));
    private final Ray r5 = new Ray(p100, v001);
    private final Ray r6 = new Ray(p04, v001);
    private final Ray r7 = new Ray(p05, v001);
    private final Ray r8 = new Ray(p120, v1);
    private final Ray r9 = new Ray(p050, v1);


    /**
     * Test method for {@link geometries.Sphere# findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        final var exp1 = List.of(gp1);

        final var exp2 = List.of(p100);


        // ============ Equivalence Partitions Tests ==============

        // TC01: ray does not vertical ant not parallel to the plane (1 point)
        final var result1 = p2.findIntersections(r1).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray not vertical and not parallel to plane - Wrong point");

        // TC02: ray does not vertical ant not parallel after the plane to the plane (0 point)
        assertNull(p2.findIntersections(r2), "calculate intersections between a plane and a" +
                " ray that is not parallel and not vertical should not have to return intersection point ");

        // =============== Boundary Values Tests ==================

        // TC11: calculate intersection points between plane and ray that parallel and on the plane (0 point)
        assertNull(p2.findIntersections(r3), "Ray is parallel to the plane and on the plane ");

        //TC12:calculate intersection points between plane and ray that parallel and not on the plane
        assertNull(p2.findIntersections(r4), "Ray is parallel to the plane and not on the plane ");

        //TC13:Calculate intersection points between plane and ray that vertical to the plane and start on it (0 point)
        assertNull(p2.findIntersections(r5),
                " Ray is vertical to the plane and start on it");

        //TC14:Calculate intersection points between plane and ray that vertical to the plane and before the plane (1 point)

        final var result2 = p2.findIntersections(r6).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p04))).toList();
        assertEquals(1, result2.size(), " Wrong number of points");
        assertEquals(exp2, result2, "Ray is vertical to the plane and before the plane");

        //TC15:Calculate intersection points between plane and ray that vertical to the plane and start after it (0 point)
        assertNull(p2.findIntersections(r7),
                " Ray is vertical to the plane and start after the plane");

        //TC16:Calculate intersection points between plane and ray that do not vertical ant not parallel to the plane (0 point)
        // and start on point which the plane represent by it

        assertNull(p2.findIntersections(r8),
                " Ray is not vertical and not parallel to the plane and start on point which the plane represent by it");

        //TC17:Calculate intersection points between plane and ray that does not vertical ant not parallel to the plane
        //and start on the plane (0 point)

        assertNull(p2.findIntersections(r9),
                " Ray is not vertical and not parallel to the plane and start on the plane");
    }

    @Test
    void testFindGeoIntersectionsHelper() {

        final var exp1 = List.of(new Intersectable.GeoPoint(p2, gp1));

        final var exp2 = List.of(new Intersectable.GeoPoint(p2, p100));


        // ============ Equivalence Partitions Tests ==============

        // TC01: ray does not vertical ant not parallel to the plane (1 point) maxDistance = 1.23
        final var result1 = p2.findGeoIntersections(r1, 1.23).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p01))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray not vertical and not parallel to plane - Wrong point");

        //TC01*: ray does not vertical ant not parallel to the plane (1 point) maxDistance = 1
        assertNull(p2.findGeoIntersectionsHelper(r1, 1), "Ray not vertical and not parallel to plane- no intersection point in this distance");

        // TC02: ray does not vertical ant not parallel after the plane to the plane (0 point)
        assertNull(p2.findGeoIntersectionsHelper(r2, Double.POSITIVE_INFINITY),
                "ray that is not parallel and not vertical should not have to return intersection point");

        // =============== Boundary Values Tests ==================

        // TC11: calculate intersection points between plane and ray that parallel and on the plane (0 point)
        assertNull(p2.findGeoIntersectionsHelper(r3,Double.POSITIVE_INFINITY), "Ray is parallel to the plane and on the plane ");

        //TC12:calculate intersection points between plane and ray that parallel and not on the plane
        assertNull(p2.findGeoIntersectionsHelper(r4,Double.POSITIVE_INFINITY), "Ray is parallel to the plane and not on the plane ");

        //TC13:Calculate intersection points between plane and ray that vertical to the plane and start on it (0 point)
        assertNull(p2.findGeoIntersectionsHelper(r5,Double.POSITIVE_INFINITY),
                " Ray is vertical to the plane and start on it");

        //TC14:Calculate intersection points between plane and ray that vertical to the plane and before the plane (1 point)
        // maxDistance = 1
        final var result2 = p2.findGeoIntersectionsHelper(r6,1).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p04))).toList();
        assertEquals(1, result2.size(), " Wrong number of intersections points in distance of 1");
        assertEquals(exp2, result2, "Ray is vertical to the plane and before the plane, max distance is infinity");

        //TC14*:Calculate intersection points between plane and ray that vertical to the plane and before the plane (1 point)
        // maxDistance = 0.5
        assertNull(p2.findGeoIntersectionsHelper(r6,0.5),
                "Ray is vertical to the plane and before the plane,wrong number of point in distance of 0.5");


        //TC15:Calculate intersection points between plane and ray that vertical to the plane and start after it (0 point)
        assertNull(p2.findGeoIntersectionsHelper(r7,Double.POSITIVE_INFINITY),
                " Ray is vertical to the plane and start after the plane");

        //TC16:Calculate intersection points between plane and ray that do not vertical ant not parallel to the plane (0 point)
        // and start on point which the plane represent by it

        assertNull(p2.findGeoIntersectionsHelper(r8,Double.POSITIVE_INFINITY),
                " Ray is not vertical and not parallel to the plane and start on point which the plane represent by it");

        //TC17:Calculate intersection points between plane and ray that does not vertical ant not parallel to the plane
        //and start on the plane (0 point)

        assertNull(p2.findGeoIntersectionsHelper(r9,Double.POSITIVE_INFINITY),
                " Ray is not vertical and not parallel to the plane and start on the plane");
    }
}