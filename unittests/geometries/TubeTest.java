package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TubeTest {

    /**
     * Test method for
     * {@link primitives.Point constructor(Point)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some tube
        Tube t = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 3d);
        assertNotNull(t, "Failed constructing a Tube");
    }

    @Test
    void testGetNormal() {
        Point p = new Point(3, 0, 0);//point on the tube for equivalence partitions test
        Tube tTube = new Tube(new Ray(new Point(0, 3, 0), new Vector(1, 0, 0)), 3d);//p0=(0,3,0)

        // ============ Equivalence Partitions Tests ==============

        // TC01: A test for the correct calculation of the normal of a certain tube
        assertEquals(new Vector(0, -1, 0),
                tTube.getNormal(p),
                "normal of Tube is incorrect");

        // =============== Boundary Values Tests ==================

        //TC11: Test for a case where the vector (P-O) is perpendicular to the ray
        assertEquals(new Vector(0, 1, 0), tTube.getNormal(new Point(0, 4, 0)),
                "incorrect calculating normal on case:The Vector (p-p0) is orthogonal to vector direction of ray's tube ");

    }

    @Test
    public void testFindIntersections() {


        // ============ Equivalence Partitions Tests ==============

    }

}