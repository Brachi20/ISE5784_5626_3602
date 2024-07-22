package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    private final Point p100 = new Point(1, 0, 0);
    private final Point p110 = new Point(1, 1, 0);
    private final Point p301 = new Point(3, 0, 1);
    private final Point p210 = new Point(2d, 1d, 0);
    private final Point p101 = new Point(1d, 0d, 1d);

    private final Vector v100 = new Vector(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * test method for {@link geometries.Sphere #Sphere(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some sphere
        Sphere s = new Sphere(p100, 3d);
        assertNotNull(s, "Failed constructing a correct Sphere");

    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test calculate normal

        Sphere s = new Sphere(p100, 3d);
        assertEquals(v100,
                s.getNormal(new Point(4, 0, 0)),
                "normal of Sphere is incorrect");

    }

    //variable for testFindIntersections and testFindGeoIntersectionsHelper
    private final Sphere sphere = new Sphere(p100, 1d);

    private final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
    private final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);

    private final Point gp3 = new Point(1.7032248981570, 0.71096746944711, 0);

    private final Point gp4 = new Point(1.92307692307692, -0.38461538461538, 0);

    private final Point gp5 = new Point(1.70710678118654, 0.70710678118654, 0);
    private final Point gp6 = new Point(0.2928932188134, -0.7071067811865, 0);

    private final Point gp7 = new Point(1d, -1d, 0d);

    private final Point gp8 = new Point(1.55470019622522, -0.83205029433784, 0.0);

    private final Vector v310 = new Vector(3, 1, 0);
    private final Vector v110 = new Vector(1, 1, 0);
    private final Vector v320 = new Vector(3, 2, 0);
    private final Vector v = new Vector(0, -1, 0);
    private final Vector v1 = new Vector(1, -1.5, 0d);
    private final Vector v2 = new Vector(5, 1.5, 0);
    private final Vector v3 = new Vector(-1, -1, 0);
    private final Vector v4 = new Vector(0, 1.5, 0);

    private final Point p01 = new Point(-1d, 0d, 0d);
    private final Point p1 = new Point(1d, 0.5, 0d);
    private final Point p2 = new Point(1d, 1.5, 0d);
    private final Point p3 = new Point(-1d, 0d, 1d);

    /**
     * Test method for {@link geometries.Sphere# findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        final var exp = List.of(gp1, gp2);
        final var exp1 = List.of(gp3);
        final var exp2 = List.of(gp4);
        final var exp3 = List.of(gp5, gp6);
        final var exp4 = List.of(gp7);
        final var exp5 = List.of(gp8);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(p1, v2))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp1, result2, "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p210, v320)),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)


        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(p110, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p110))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp2, result3, "Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p110, v320)),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(p210, v3))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p210))).toList();
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp3, result4, "Ray crosses sphere by the center");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result5 = sphere.findIntersections(new Ray(p110, v))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p110))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp4, result5, "Ray starts at sphere and goes inside by the center");

        // TC15: Ray starts inside (1 point)
        final var result6 = sphere.findIntersections(new Ray(p1, v))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(exp4, result6, "Ray starts inside the sphere and goes outside by the center");

        // TC16: Ray starts at the center (1 point)
        final var result7 = sphere.findIntersections(new Ray(p100, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p100))).toList();
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(exp5, result7, "Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp8, v1)),
                "Ray starts at sphere and goes outside by the center");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p2, v4)),
                "Ray starts after sphere by the center");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(p3, v100)), "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p101, v)), "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(p301, v100)), "Ray starts after the tangent point");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(p210, v001)),
                "Ray's line is outside the sphere and orthogonal to ray start to sphere's center line");
    }

    @Test
    public void testFindGeoIntersectionsHelper(){
        final var exp = List.of(new Intersectable.GeoPoint(sphere,gp1),new Intersectable.GeoPoint(sphere, gp2));
        final var exp1 = List.of(new Intersectable.GeoPoint(sphere, gp3));
        final var exp2 = List.of(new Intersectable.GeoPoint(sphere, gp4));
        final var exp3 = List.of(new Intersectable.GeoPoint(sphere, gp5), new Intersectable.GeoPoint(sphere, gp6));
        final var exp4 = List.of(new Intersectable.GeoPoint(sphere, gp7));
        final var exp5 = List.of(new Intersectable.GeoPoint(sphere, gp8));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p01, v110),Double.POSITIVE_INFINITY),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        //maxDistance = 2.7
        final var result1 = sphere.findGeoIntersectionsHelper(new Ray(p01, v310),2.7)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        //maxDistance = 0.74
        final var result2 = sphere.findGeoIntersectionsHelper(new Ray(p1, v2),0.74)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p1))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp1, result2, "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p210, v320),Double.POSITIVE_INFINITY),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)


        // TC11: Ray starts at sphere and goes inside (1 point)
        //maxDistance = 1.67
        final var result3 = sphere.findGeoIntersectionsHelper(new Ray(p110, v1),1.67)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p110))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp2, result3, "Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p110, v320),Double.POSITIVE_INFINITY),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        //maxDistance = 2.42
        final var result4 = sphere.findGeoIntersectionsHelper(new Ray(p210, v3),2.42)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p210))).toList();
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp3, result4, "Ray crosses sphere by the center");

        // TC14: Ray starts at sphere and goes inside (1 point)
        //maxDistance = 2
        final var result5 = sphere.findGeoIntersectionsHelper(new Ray(p110, v),2d)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p110))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp4, result5, "Ray starts at sphere and goes inside by the center");

        // TC15: Ray starts inside (1 point)
        //maxDistance = 1.5
        final var result6 = sphere.findGeoIntersectionsHelper(new Ray(p1, v),1.5)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p1))).toList();
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(exp4, result6, "Ray starts inside the sphere and goes outside by the center");

        // TC16: Ray starts at the center (1 point)
        //maxDistance = 1
        final var result7 = sphere.findGeoIntersectionsHelper(new Ray(p100, v1),1d)
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p100))).toList();
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(exp5, result7, "Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(gp8, v1),Double.POSITIVE_INFINITY),
                "Ray starts at sphere and goes outside by the center");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p2, v4),Double.POSITIVE_INFINITY),
                "Ray starts after sphere by the center");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p3, v100),Double.POSITIVE_INFINITY), "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p101, v),Double.POSITIVE_INFINITY), "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p301, v100),Double.POSITIVE_INFINITY), "Ray starts after the tangent point");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(p210, v001),Double.POSITIVE_INFINITY),
                "Ray's line is outside the sphere and orthogonal to ray start to sphere's center line");


    }
}