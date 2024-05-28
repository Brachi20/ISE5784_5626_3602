package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1=new Point(0d,5d,0d);
    Point p2=new Point(1d,0d,0d);
    Point p3=new Point(1d,5d,0d);
    Point p4=new Point(1d,2d,0d);
    Plane p=new Plane(p1,p2,p4);
    @Test
    public void testConstructor(){

        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some point
        assertNotNull(p,"Failed constructing a plane");

        // =============== Boundary Values Tests ==================

        // TC11: A test for building a plan with coalesce points
        assertThrows(IllegalArgumentException.class,()->new Plane(p1, p1, p2),
                "ERROR:coalesce point in plane, did not throw an exception");
        // TC12: A test for building a plan with 3 point on the same line
        assertThrows(IllegalArgumentException.class,()->new Plane(p1,p2,p3),
                "ERROR: the points are on the same line");
    }

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(p.getNormal().normalize().equals(new Vector(0,0,1))
                ||p.getNormal().normalize().equals(new Vector(0,0,-1)),
                "ERROR:normal of plane is not correct");
    }
}