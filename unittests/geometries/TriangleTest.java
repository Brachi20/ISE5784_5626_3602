package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TriangleTest {

    private final Point p000 = new Point(0, 0, 0);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    private final Point p001 = new Point(0, 0, 1);

    /**
     * Test method for {@link geometries.Triangle #FindIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Triangle t1 = new Triangle(p000, p100, p010);

        final Point gp1 = new Point(0.25, 0.25, 0);
        final var exp = List.of(gp1);

        final Vector v = new Vector(0, 0, -1);

        final Point p01 = new Point(0.25, 0.25, 1);
        final Point p02 = new Point(2, 2, 1);
        final Point p3 = new Point(0, 1.5, 1);
        final Point p22 = new Point(0.5, 0.5, 1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside the triangle(1 point)
        final var result1 = t1.findIntersections(new Ray(p01, v)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();

        assertEquals(result1.size(), 1,
                "Wrong number of points");
        assertEquals(exp, result1, "case: Ray cross the triangle");

        // TC02: Ray's line is outside the triangle opposite the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p02, v)),
                "case: Ray's line is outside the triangle");

        // TC03: Ray's line is outside the triangle against the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p3, v)),
                "case: Ray's line is outside the triangle");

        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p22, v)),
                "case: Ray's line is on the edge");

        // TC12: Ray's line is in the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p001, v)),
                "case: Ray's line is in the vertex");

        // TC13: Ray's line is on the edge's continuation(0 points)
        assertNull(t1.findIntersections(new Ray(p3, v)),
                "case: Ray's line is on the edge's continuation");

    }
}