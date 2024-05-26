package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * test method for {@link geometries.Cylinder #Cylinder(primitives.Ray...)}.
     */
    @Test
    public void testConstructor() {
        Cylinder c = new Cylinder(4d, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 3d);
        assertNotNull(c, "Failed constructing a correct Cylinder");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        Cylinder c = new Cylinder(4d, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 3d);

        // ============ Equivalence Partitions Tests ==============

        //TC01:Normal calculation test with the given point on the envelope
        assertEquals(new Vector(1, 0, 0),
                c.getNormal(new Point(3, 0, 3)),
                "normal of cylinder from point on the envelope is incorrect");
        //TC02:Normal calculation test with the given point on the button base
        assertEquals(new Vector(0, 0, 1),
                c.getNormal(new Point(1, 0, 1)),
                "normal of cylinder from point on the button base is incorrect: " +
                        "The normal returned is different from the direction vector of the ray of the cylinder axis when normalized");
        //TC03:Normal calculation test with the given point on the top base
        assertEquals(new Vector(0, 0, 1),
                c.getNormal(new Point(1, 0, 5)),
                "normal of cylinder from point on the top base is incorrect: " +
                        "The normal returned is different from the direction vector of the ray of the cylinder axis when normalized");

        // =============== Boundary Values Tests ==================

        //TC11:Test for normal calculation with the point in the middle of the button base
        assertThrows(IllegalArgumentException.class,
                () -> c.getNormal(new Point(0, 0, 1)),
                "Case - Normal calculation at the center point of the lower base, did not throw an exception");

        //TC12:Test for normal calculation with the point in the middle of the top base
        assertThrows(IllegalArgumentException.class,
                () -> c.getNormal(new Point(0, 0, 5)),
                "Case - Normal calculation at the center point of the top base, did not throw an exception");

        //TC13:Test for normal calculation with a given point in the edge of the button base
        assertThrows(IllegalArgumentException.class,
                () -> c.getNormal(new Point(3, 0, 1)),
                "Case - Normal calculation at the edge point  of the button base , did not throw an exception");

        //TC13:Test for normal calculation with a given point in the edge of the top base
        assertThrows(IllegalArgumentException.class,
                () -> c.getNormal(new Point(3, 0, 5)),
                "Case - Normal calculation at the edge point of the top base , did not throw an exception");
    }

}