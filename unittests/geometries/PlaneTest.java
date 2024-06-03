package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
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
    /**
     * Test method for {@link geometries.Sphere# findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections(){
        Vector v=new Vector(0,0,1);
        Vector v1=new Vector(1,1,2);
        Ray r1 =new Ray(new Point(0,0,-1),v1);
        Ray r2=new Ray(new Point(1,1,1),new Vector(1,1,1));
        Ray r3=new Ray(p1,new Vector(0,-5,1));
        Ray r4=new Ray(new Point(0,5,1),new Vector(0,-5,1));
        Ray r5=new Ray(p2,v);
        Ray r6=new Ray(new Point(1,0,-1),v);
        Ray r7=new Ray(new Point(1,0,1),v);
        Plane p2=new Plane(p4,v);
        Ray r8 =new Ray(p4,v1);
        Ray r9 =new Ray(p1,v1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: ray does not vertical ant not parallel to the plane
        assertEquals(1,p.findIntersections(r1).size(),"calculate intersections between a plane and a" +
                " ray that is not parallel and not vertical do not return one intersection point");

        // TC02: ray does not vertical ant not parallel to the plane without an intersection point
        assertEquals(0,p.findIntersections(r2).size(),"calculate intersections between a plane and a" +
                " ray that is not parallel and not vertical should not have to return intersection point ");

        // =============== Boundary Values Tests ==================

        // TC11: calculate intersection points between plane and ray that parallel and on the plane
        assertEquals(0,p.findIntersections(r3).size(),"calculate intersections between a plane and " +
                "a ray that parallel and on the plane should not have to return intersection point ");

        //TC12:calculate intersection points between plane and ray that parallel and not on the plane
        assertEquals(0,p.findIntersections(r4).size(),"calculate intersections between a plane and " +
                "a ray that parallel and not on the plane should not have to return intersection point ");

        //TC13:Calculate intersection points between plane and ray that parallel to the plane and start on it
        assertEquals(0,p.findIntersections(r5).size(),
                "calculate intersections between a plane and a ray that parallel to the plane and start on it " +
                        "should not have to return intersection point");

        //TC14:Calculate intersection points between plane and ray that parallel to the plane and before the plane
        assertEquals(1,p.findIntersections(r6).size(),
                "calculate intersections between a plane and a ray that parallel to the plane and start before it " +
                        "should return one intersection point");

        //TC15:Calculate intersection points between plane and ray that parallel to the plane and start after it
        assertEquals(0,p.findIntersections(r7).size(),
                "calculate intersections between a plane and a ray that parallel to the plane and start after it " +
                        "should not have to return intersection point");

        //TC16:Calculate intersection points between plane and ray that does not vertical ant not parallel to the plane
        // and start on point which the plane represent by it
        assertEquals(0,p2.findIntersections(r8).size(),
                "Calculating a cut with a non-perpendicular and non-parallel ray starting at the " +
                        "representation point should not have returned points");

        //TC17:Calculate intersection points between plane and ray that does not vertical ant not parallel to the plane
        //and start on the plane
        assertEquals(0,p2.findIntersections(r9).size(),
                "Calculating a cut with a non-perpendicular and non-parallel ray starting at the " +
                        "plane should not have returned points");



    }
}