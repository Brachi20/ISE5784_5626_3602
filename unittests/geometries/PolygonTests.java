package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan Zilberstein
 */
public class PolygonTests {


    private final Point p000 = new Point(0, 0, 0);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    private final Point p001 = new Point(0, 0, 1);

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {

        final Point p1 = new Point(-1, 1, 1);
        final Point p03 = new Point(0, 2, 2);
        final Point p04 = new Point(0.5, 0.25, 0.5);
        final Point p11 = new Point(0, 0.5, 0.5);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(p001,
                        p100,
                        p010,
                        p1),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p010, p100, p1), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p100, p010, p03), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p100, p010,
                        p04), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p100, p010,
                        p11),
                "Constructed a polygon with vertex on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p100, p010, p001),
                "Constructed a polygon with vertices on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(p001, p100, p010, p010),
                "Constructed a polygon with vertices on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        final Point p01 = new Point(-1, 1, 1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {p001, p100, p010, p01};
        Polygon pol = new Polygon(pts);

        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(p001), "");

        // generate the test result
        Vector result = pol.getNormal(p001);

        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");

        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Polygon #FindIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Polygon t1 = new Polygon(p000, p100, new Point(1, 1, 0), p010);

        final Point gp1 = new Point(0.25, 0.5, 0);
        final var exp = List.of(gp1);

        final Point p01 = new Point(0.25, 0.5, 1);
        final Point p02 = new Point(-1, 0.75, 1);
        final Point p03 = new Point(1.5, 0, 1);
        final Point p11 = new Point(0, 0.5, 1);
        final Point p13 = new Point(0, 1.5, 1);


        final Vector v = new Vector(0, 0, -1);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside the polygon(1 point)
        final var result1 = t1.findIntersections(new Ray(p01, v)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();

        assertEquals(1, result1.size(),
                "Wrong number of points");
        assertEquals(exp, result1,
                "Ray's line cross the polygon");

        // TC02: Ray's line is outside the polygon opposite the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p02, v)),
                "case: Ray's line is outside the polygon");

        // TC03: Ray's line is outside the polygon against the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p03, v)),
                "case: Ray's line is outside the polygon");

        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on the edge(0 points)
        assertNull(t1.findIntersections(new Ray(p11, v)),
                "case: Ray's line is on the edge");

        // TC12: Ray's line is in the vertex(0 points)
        assertNull(t1.findIntersections(new Ray(p001, v)),
                "case: Ray's line is in the vertex");

        // TC13: Ray's line is on the edge's continuation(0 points)
        assertNull(t1.findIntersections(new Ray(p13, v)),
                "case: Ray's line is on the edge's continuation");

    }

}
