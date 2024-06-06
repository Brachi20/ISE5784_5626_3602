package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /** Test method for {@link geometries.Triangle #FindIntersections(primitives.Ray)}. */
    @Test
    void testFindIntersections() {

        Vector v = new Vector(0, 0, -1);
        Point p000= new Point(0, 0, 0);
        Point p100= new Point(1, 0, 0);
        Point p010= new Point(0, 1, 0);
        Point p001= new Point(0,0, 1);
        Point p1= new Point(0.25, 0.25, 1);
        Point p221= new Point(2, 2, 1);
        Point p2= new Point(0.5, 0.5, 1);
        Point p3= new Point(0, 1.5, 1);
        Triangle t1 = new Triangle(p000, p100, p010);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside the triangle(1 point)
        assertEquals(t1.findIntersections(new Ray(p1, v)).size(), 1,
                "case: Ray's line is inside the triangle needed to find one intersection point");

        // TC02: Ray's line is outside the triangle opposite the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p221, v)),
                "case: Ray's line is outside the triangle opposite the edge needed to find no intersection point");

        // TC03: Ray's line is outside the triangle against the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p3, v)),
                "case: Ray's line is outside the triangle against the vertex needed to find no intersection point");

        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p2, v)),
                "case: Ray's line is on the edge needed to find no intersection point");

        // TC12: Ray's line is in the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p001, v)),
                "case: Ray's line is in the vertex needed to find no intersection point");

        // TC13: Ray's line is on the edge's continuation(0 points)
        assertNull(t1.findIntersections(new Ray(p3, v)),
                "case: Ray's line is on the edge's continuation needed to find no intersection point");




    }
}