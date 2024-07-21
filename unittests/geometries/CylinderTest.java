package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    private final Point p000 = new Point(0, 0, 0);
    private final Point p001 = new Point(0, 0, 1);

    private final Vector v001 = new Vector(0, 0, 1);
    private final Vector v010 = new Vector(0, 1, 0);
    private final Vector v100 = new Vector(1, 0, 0);

    /**
     * test method for {@link geometries.Cylinder #Cylinder(primitives.Ray...)}.
     */
    @Test
    public void testConstructor() {
        Cylinder c = new Cylinder(4d, new Ray(p001, v001), 3d);
        assertNotNull(c, "Failed constructing a correct Cylinder");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        final Point p005 = new Point(0, 0, 5);

        Cylinder c1 = new Cylinder(4d, new Ray(p001, v001), 3d);
        Cylinder c2 = new Cylinder(4d, new Ray(p005, v001), 3d);

        final Point p01 = new Point(3, 0, 3);
        final Point p02 = new Point(1, 0, 1);
        final Point p03 = new Point(1, 0, 5);


        // ============ Equivalence Partitions Tests ==============

        //TC01:Normal calculation test with the given point on the envelope
        assertEquals(v100,
                c1.getNormal(p01),
                "normal of cylinder from point on the envelope is incorrect");

        //TC02:Normal calculation test with the given point on the button base
        assertEquals(v001,
                c1.getNormal(p02),
                "Normal calculation in case: the point P0 on the button base,is incorrect");

        //TC03:Normal calculation test with the given point on the top base
        assertEquals(v001,
                c2.getNormal(p03),
                "Normal calculation in case: the point P0 on the top base,is incorrect");

        // =============== Boundary Values Tests ==================

        //TC11:Test for normal calculation with the point in the middle of the button base
        assertEquals(v001,
                c1.getNormal(p001),
                "Case - Normal calculation at the center point of the lower base, is incorrect");

        //TC12:Test for normal calculation with the point in the middle of the top base
        assertEquals(v001,
                c2.getNormal(p005),
                "Case - Normal calculation at the center point of the top base, is incorrect");


    }


    /**
     * Test method for {@link geometries.Cylinder #FindIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Cylinder c1 = new Cylinder(4d, new Ray(p000, v001), 1d);

        final Point gp01 = new Point(-1, 0, 0.5);
        final Point gp02 = new Point(1, 0, 1.5);
        final var exp01 = List.of(gp01, gp02);

        final Point gp21 = new Point(1, 0, 1.25);
        final var exp21 = List.of(gp21);

        final Point gp31 = new Point(-1, 0, 2);
        final var exp31 = List.of(gp31);

        final Point gp41 = new Point(0.5, 0, 4);
        final var exp41 = List.of(gp41);


        Point p101 = new Point(1, 0, 1);
        Point p1 = new Point(1, -1, 1);
        Point p2 = new Point(0.5, 0, 1);

        Point p02 = new Point(-2, 0, 0);
        Point p11 = new Point(1, 0, -1);

        Vector v201 = new Vector(2, 0, 1);
        Vector v1 = new Vector(-2, 0, 1);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(c1.findIntersections(new Ray(p1, v201)),
                "Ray's line out of cylinder");

        // TC02:Ray starts before and crosses the cylinder (2 points)
        final var result1 = c1.findIntersections(new Ray(p02, v201)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p02))).toList();

        assertEquals(2, result1.size(),
                "Wrong number of points");
        assertEquals(exp01, result1,
                "Ray crosses the cylinder");

        //TC03: Ray's line is starts inside the cylinder and goes outside it (1 point)
        final var result2 = c1.findIntersections(new Ray(p2, v201)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p2))).toList();

        assertEquals(1, result2.size(),
                "Wrong number of points");
        assertEquals(exp21, result2, "Ray's line inside the cylinder");

        //============ Boundary Values Tests ==================

        //Group:Ray's line is on the surface of the cylinder
        // TC11: Ray's line is on the cylinder's surface and parallel to the cylinder's axis (0 points)
        assertNull(c1.findIntersections(new Ray(p11, v001)),
                "Ray's line on the surface");

        // TC12: Ray's line is on the cylinder's surface and goes outside it (0 points)
        assertNull(c1.findIntersections(new Ray(p101, v201)),
                "Ray's line on the surface");

        // TC13: Ray's line is on the cylinder's surface and goes inside it (1 point)
        final var result3 = c1.findIntersections(new Ray(p101, v1)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p101))).toList();

        assertEquals(1, result3.size(),
                "Wrong number of points");
        assertEquals(exp31, result3, "Ray's line on the surface, goes inside the cylinder");

        //Group:Ray's line is parallel to the cylinder's axis
        // TC14: Ray's line is outside the cylinder and parallel to the cylinder's axis (0 points)
        assertNull(c1.findIntersections(new Ray(p1, v001)),
                "Ray's line outside the cylinder");

        //TC15: Ray's line is starts inside the cylinder and parallel to the cylinder's axis (1 point)
        final var result4 = c1.findIntersections(new Ray(p2, v001)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p2))).toList();

        assertEquals(1, result4.size(),
                "Wrong number of points");
        assertEquals(exp41, result4, "Ray's line inside the cylinder");

        //TC16:Ray's line starts from a point outside the cylinder and tangent to the cylinder(0 point)
        assertNull(c1.findIntersections(new Ray(p1, v010)),
                "Ray's line is tangent to the cylinder");


    }

    /**
     * Test method for {@link geometries.Cylinder #findGeoIntersectionsHelper(primitives.Ray, double)}.
     */

    @Test
    void testFindGeoIntersectionsHelper() {
        Cylinder c1 = new Cylinder(4d, new Ray(p000, v001), 1d);

        final Point gp01 = new Point(-1, 0, 0.5);
        final Point gp02 = new Point(1, 0, 1.5);
        final var exp01 = List.of(new Intersectable.GeoPoint(c1,gp01), new Intersectable.GeoPoint(c1,gp02));

        final Point gp21 = new Point(1, 0, 1.25);
        final var exp21 = List.of(new Intersectable.GeoPoint(c1,gp21));

        final Point gp31 = new Point(-1, 0, 2);
        final var exp31 = List.of(new Intersectable.GeoPoint(c1,gp31));

        final Point gp41 = new Point(0.5, 0, 4);
        final var exp41 = List.of(new Intersectable.GeoPoint(c1,gp41));


        Point p101 = new Point(1, 0, 1);
        Point p1 = new Point(1, -1, 1);
        Point p2 = new Point(0.5, 0, 1);

        Point p02 = new Point(-2, 0, 0);
        Point p11 = new Point(1, 0, -1);

        Vector v201 = new Vector(2, 0, 1);
        Vector v1 = new Vector(-2, 0, 1);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p1, v201),Double.POSITIVE_INFINITY),
                "Ray's line out of cylinder");

        // TC02:Ray starts before and crosses the cylinder (2 points)
        final var result1 = c1.findGeoIntersectionsHelper(new Ray(p02, v201),3.36).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p02))).toList();

        assertEquals(2, result1.size(),
                "Wrong number of points, three are 2 points of intersection in the range of 3.35 from the head of the ray");
        assertEquals(exp01, result1,
                "Ray crosses the cylinder");

        //TC03: Ray's line is starts inside the cylinder and goes outside it (1 point)
        final var result2 = c1.findGeoIntersectionsHelper(new Ray(p2, v201),0.56).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p2))).toList();

        assertEquals(1, result2.size(),
                "Wrong number of points, there is 1 point of intersection in the range of 0.56 from the head of the ray");
        assertEquals(exp21, result2, "Ray's line inside the cylinder");

        //============ Boundary Values Tests ==================

        //Group:Ray's line is on the surface of the cylinder
        // TC11: Ray's line is on the cylinder's surface and parallel to the cylinder's axis (0 points)
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p11, v001),Double.POSITIVE_INFINITY),
                "Ray's line on the surface");

        // TC12: Ray's line is on the cylinder's surface and goes outside it (0 points)
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p101, v201),Double.POSITIVE_INFINITY),
                "Ray's line on the surface");

        // TC13: Ray's line is on the cylinder's surface and goes inside it (1 point)
        final var result3 = c1.findGeoIntersectionsHelper(new Ray(p101, v1),2.24).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p101))).toList();

        assertEquals(1, result3.size(),
                "Wrong number of points, there is 1 point of intersection in the range of 2.24 from the head of the ray");
        assertEquals(exp31, result3, "Ray's line on the surface, goes inside the cylinder");

        //Group:Ray's line is parallel to the cylinder's axis
        // TC14: Ray's line is outside the cylinder and parallel to the cylinder's axis (0 points)
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p1, v001),Double.POSITIVE_INFINITY),
                "Ray's line outside the cylinder");

        //TC15: Ray's line is starts inside the cylinder and parallel to the cylinder's axis (1 point)
        final var result4 = c1.findGeoIntersectionsHelper(new Ray(p2, v001),3d).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p2))).toList();

        assertEquals(1, result4.size(),
                "Wrong number of points, there is 1 point of intersection in the range of 3 from the head of the ray");
        assertEquals(exp41, result4, "Ray's line inside the cylinder");

        //TC16:Ray's line starts from a point outside the cylinder and tangent to the cylinder(0 point)
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p1, v010),Double.POSITIVE_INFINITY),
                "Ray's line is tangent to the cylinder");

        //TC17: case of TC13 with maxDistance = 2.23
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p101, v1),2.23),
                "Ray's line on the surface, goes inside the cylinder, but the point is out of the range");

        //TC18: case of TC15 with maxDistance = 2.9
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p2, v001),2.9),
                "Ray's line inside the cylinder, but the point is out of the range");

        //TC19: case of TC02 with maxDistance = 2
        final var result5 = c1.findGeoIntersectionsHelper(new Ray(p02, v201),2d).stream()
                .sorted(Comparator.comparingDouble(p -> p.point.distance(p02))).toList();
        assertEquals(1, result5.size(),
                "Wrong number of points, there is 1 point of intersection in the range of 2 from the head of the ray");
        assertEquals(List.of(exp01.get(0)), result5,
                "Ray crosses the cylinder, wrong calculated point");
        //TC20:case of TC02 with maxDistance = 0.25
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p02, v201),0.25),
                "Ray crosses the cylinder, but the point is out of the range");

        //TC21: case of TC03 with maxDistance = 0.5
        assertNull(c1.findGeoIntersectionsHelper(new Ray(p2, v201),0.5),
                "Ray's line inside the cylinder, but the point is out of the range");




    }


}