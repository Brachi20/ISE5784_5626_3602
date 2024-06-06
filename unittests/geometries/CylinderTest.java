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
        Cylinder c1 = new Cylinder(4d, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 3d);
        Cylinder c2 = new Cylinder(4d, new Ray(new Point(0, 0, 5), new Vector(0, 0, 1)), 3d);

        // ============ Equivalence Partitions Tests ==============

        //TC01:Normal calculation test with the given point on the envelope
        assertEquals(new Vector(1, 0, 0),
                c1.getNormal(new Point(3, 0, 3)),
                "normal of cylinder from point on the envelope is incorrect");
        //TC02:Normal calculation test with the given point on the button base
        assertEquals(new Vector(0, 0, 1),
                c1.getNormal(new Point(1, 0, 1)),
                "Normal calculation in case: the point P0 on the button base,is incorrect");
        //TC03:Normal calculation test with the given point on the top base
        assertEquals(new Vector(0, 0, 1),
                c2.getNormal(new Point(1, 0, 5)),
                "Normal calculation in case: the point P0 on the top base,is incorrect");

        // =============== Boundary Values Tests ==================

        //TC11:Test for normal calculation with the point in the middle of the button base
        assertEquals(new Vector(0, 0, 1),
                c1.getNormal(new Point(0, 0, 1)),
                "Case - Normal calculation at the center point of the lower base, is incorrect");

        //TC12:Test for normal calculation with the point in the middle of the top base
        assertEquals(new Vector(0, 0, 1),
                c2.getNormal(new Point(0, 0, 5)),
                "Case - Normal calculation at the center point of the top base, is incorrect");


    }


    /**
     * Test method for {@link geometries.Cylinder #FindIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Point p000 = new Point(0, 0, 0);
        Point p001 = new Point(0, 0, 1);
        Point p101 = new Point(1, 0, 1);
        Vector v001 = new Vector(0, 0, 1);
        Vector v010 = new Vector(0, 1, 0);
        Vector v201= new Vector(2, 0, 1);
        Vector v2= new Vector(-2, 0, 1);
        Point p1=new Point(1, -1, 1);
        Point p2=new Point(-2, 0, 0);
        Point p3=new Point(1, 0, -1);
        Point p4=new Point(0.5, 0, 1);
        Cylinder c1 = new Cylinder(4d, new Ray(p000, v001), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(c1.findIntersections(new Ray(p1, v201)),
                "Ray's line is outside the cylinder should not have intersection points");

        // TC02: A ray that intersects the cylinder at two points(2 points)
        assertEquals(2, c1.findIntersections(new Ray(p2, v201)).size(),
                "Ray's line is inside the cylinder should have 2 intersection points");

        //TC03: Ray's line is starts inside the cylinder and goes outside it (1 point)
        assertEquals(1, c1.findIntersections(new Ray(p4, v201)).size(),
                "Ray's line is starts inside the cylinder and goes outside it should have 1 intersection point");

        //============ Boundary Values Tests ==================

        // TC11: Ray's line is on the cylinder's surface and parallel to the cylinder's axis (0 points)
        assertNull(c1.findIntersections(new Ray(p3, v001)),
                "Ray's line is on the cylinder's surface and parallel to the cylinder's axis should not have intersection points");

        // TC12: Ray's line is on the cylinder's surface and goes outside it (0 points)
        assertNull(c1.findIntersections(new Ray(p101, v201)),
                "Ray's line is on the cylinder's surface and goes outside it should not have intersection points");

        // TC13: Ray's line is on the cylinder's surface and goes inside it (1 point)
        assertEquals(1, c1.findIntersections(new Ray(p101, v2)).size(),
                "Ray's line is on the cylinder's surface and goes inside it should have 1 intersection point");

        // TC14: Ray's line is outside the cylinder and parallel to the cylinder's axis (0 points)
        assertNull(c1.findIntersections(new Ray(p1, v001)),
                "Ray's line is outside the cylinder and parallel to the cylinder's axis should not have intersection points");

        //TC15: Ray's line is starts inside the cylinder and parallel to the cylinder's axis (1 point)
        assertEquals(1, c1.findIntersections(new Ray(p4, v001)).size(),
               "Ray's line is starts inside the cylinder and parallel to the cylinder's axis should have 1 intersection point");

        //TC16: A ray that starts from a point outside the cylinder and launches in front of it(0 point)
        assertNull(c1.findIntersections(new Ray(p1, v010)),
                "A ray that starts from a point outside the cylinder and launches in front of it should not have intersection points");








    }
}