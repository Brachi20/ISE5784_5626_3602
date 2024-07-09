package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTest {
    private final Point p000 = new Point(0, 0, 0);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);

    private final Vector v001 = new Vector(0, 0, 1);

    @Test
    void testFindIntersections() {
        final Point p120 = new Point(1d, 2d, 0d);

        Triangle triangle = new Triangle(p000, p100, p010);
        Sphere sphere = new Sphere(p100, 1d);
        Plane plane = new Plane(p120, v001);
        Cylinder cylinder = new Cylinder(4d, new Ray(p000, v001), 1d);
        Polygon polygon = new Polygon(p000, p100, new Point(1, 1, 0), p010);
        Geometries geometries = new Geometries(triangle, sphere, plane, cylinder, polygon);
        Geometries g1 = new Geometries();

        // ============ Equivalence Partitions Tests ==============

        // TC01: Some geometries have intersections
        assertEquals(2, geometries.findIntersections(new Ray(new Point(1, 0, -1), v001)).size(),
                "Wrong number of points");

        //============ Boundary Values Tests ==================

        // TC11: Empty geometries list (0 points)
        assertNull(g1.findIntersections(new Ray(new Point(1, 0, -1), v001)),
                "Empty geometries list");

        // TC12: No geometry has intersections (0 points)
        assertNull(geometries.findIntersections(new Ray(new Point(-2, 0, 0), v001)),
                "No geometry has intersections");

        // TC13: Only one geometry has an intersection (1 point)
        assertEquals(1, geometries.findIntersections(new Ray(new Point(-2, 0, -1), v001)).size(),
                "Wrong number of points");
        // TC14: All geometries have intersections (7 points)
        assertEquals(7, geometries.findIntersections(new Ray(new Point(0.25, 0.5, -1), v001)).size(),
                "Wrong number of points");
    }
}