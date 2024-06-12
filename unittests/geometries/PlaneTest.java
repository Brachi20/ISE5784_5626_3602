package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p050 =new Point(0d,5d,0d);
    Point p100 =new Point(1d,0d,0d);
    Point p1 =new Point(-1d,10d,0d);
    Point p120 =new Point(1d,2d,0d);
    Plane p=new Plane(p050, p100, p120);

    /**
     * test method for {@link geometries.Plane #Plane(primitives.Point...)}.
     */
    @Test
    public void testConstructor(){

        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some point
        assertNotNull(p,"Failed constructing a plane");

        // =============== Boundary Values Tests ==================

        // TC11: A test for building a plan with coalesce points
        assertThrows(IllegalArgumentException.class,()->new Plane(p050, p050, p100),
                "ERROR:coalesce point in plane, did not throw an exception");
        // TC12: A test for building a plan with 3 point on the same line
        assertThrows(IllegalArgumentException.class,()->new Plane(p050, p100, p1),
                "ERROR: the points are on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(p.getNormal().normalize().equals(new Vector(0,0,1))
                ||p.getNormal().normalize().equals(new Vector(0,0,-1)),
                "ERROR:normal of plane is not correct");
    }
    /**
     * Test method for {@link geometries.Sphere# findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections(){
        Vector v=new Vector(0,0,1);
        Vector v1=new Vector(1,1,2);
        Ray r1 =new Ray(new Point(0,0,-1),v1);
        Ray r2=new Ray(new Point(1,1,1),v1);
        Ray r3=new Ray(p050,new Vector(1d,1d,0d));
        Ray r4=new Ray(new Point(0,5,1),new Vector(0,-10,0));
        Ray r5=new Ray(p100,v);
        Ray r6=new Ray(new Point(1,0,-1),v);
        Ray r7=new Ray(new Point(1,0,1),v);
        Ray r8 =new Ray(p120,v1);
        Ray r9 =new Ray(p050,v1);
        Plane p2=new Plane(p120,v);

        // ============ Equivalence Partitions Tests ==============

        // TC01: ray does not vertical ant not parallel to the plane (1 point)
        assertEquals(1,p2.findIntersections(r1).size(),"Wrong number of points");
        assertEquals(new Point(0.5,0.5,0),p2.findIntersections(r1).getFirst(),"Ray not vertical and not parallel to plane - Wrong point");
        // TC02: ray does not vertical ant not parallel after the plane to the plane (0 point)
        assertNull(p2.findIntersections(r2),"calculate intersections between a plane and a" +
                " ray that is not parallel and not vertical should not have to return intersection point ");

        // =============== Boundary Values Tests ==================

        // TC11: calculate intersection points between plane and ray that parallel and on the plane (0 point)
        assertNull(p2.findIntersections(r3),"Ray is parallel to the plane and on the plane ");

        //TC12:calculate intersection points between plane and ray that parallel and not on the plane
        assertNull(p2.findIntersections(r4),"Ray is parallel to the plane and not on the plane ");

        //TC13:Calculate intersection points between plane and ray that vertical to the plane and start on it (0 point)
        assertNull(p2.findIntersections(r5),
                " Ray is vertical to the plane and start on it");

        //TC14:Calculate intersection points between plane and ray that vertical to the plane and before the plane (1 point)
        assertEquals(1,p2.findIntersections(r6).size(), " Worn number of points");
        assertEquals(new Point(1,0,0),p2.findIntersections(r6).getFirst(),"Ray is vertical to the plane and before the plane");

        //TC15:Calculate intersection points between plane and ray that vertical to the plane and start after it (0 point)
        assertNull(p2.findIntersections(r7),
                " Ray is vertical to the plane and start after the plane");

        //TC16:Calculate intersection points between plane and ray that do not vertical ant not parallel to the plane (0 point)
        // and start on point which the plane represent by it
        assertNull(p2.findIntersections(r8),
                " Ray is not vertical and not parallel to the plane and start on point which the plane represent by it");

        //TC17:Calculate intersection points between plane and ray that does not vertical ant not parallel to the plane
        //and start on the plane (0 point)
        assertNull(p2.findIntersections(r9),
                " Ray is not vertical and not parallel to the plane and start on the plane");
    }
}