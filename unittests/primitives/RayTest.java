package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTest {

    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: t is positive
        Ray r = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        assertEquals(new Point(1, 0, 2), r.getPoint(2), "get point is incorrect");

        // TC02: t is negative
        Ray r1 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        assertEquals(new Point(1, 0, -2), r.getPoint(-2), "get point is incorrect");

        //============ Boundary Values Tests ==================
        // TC03: t is zero
        Ray r2 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        assertEquals(new Point(1, 0, 0), r.getPoint(0), "get point is incorrect");
    }

    @Test
    void testFindClosestPoint() {

        Ray r = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        Point p1 = new Point(1, 0, 1);
        Point p2 = new Point(1, 0, 2);
        Point p3 = new Point(1, 0, 3);
        Point p4 = new Point(1, 0, 4);
        Point p5 = new Point(1, 0, 5);
        Point p6 = new Point(1, 0, 6);
        Point p7 = new Point(1, 0, 7);
        Point p8 = new Point(1, 0, 8);
        Point p9 = new Point(1, 0, 9);
        Point p10 = new Point(1, 0, 10);
        Point p11 = new Point(1, 0, 11);
        Point p12 = new Point(1, 0, 12);
        Point p13 = new Point(1, 0, 13);
        Point p14 = new Point(1, 0, 14);
        Point p15 = new Point(1, 0, 15);
        Point p16 = new Point(1, 0, 16);
        Point p17 = new Point(1, 0, 17);
        Point p18 = new Point(1, 0, 18);
        Point p19 = new Point(1, 0, 19);
        Point p20 = new Point(1, 0, 20);
        Point p21 = new Point(1, 0, 21);
        Point p22 = new Point(1, 0, 22);
        Point p23 = new Point(1, 0, 23);
        Point p24 = new Point(1, 0, 24);
        Point p25 = new Point(1, 0, 25);
        Point p26 = new Point(1, 0, 26);
        Point p27 = new Point(1, 0, 27);
        List<Point> points01 = List.of(p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p1, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27);
        List<Point> p0 = new LinkedList<Point>() {
        };


        // ============ Equivalence Partitions Tests ==============

        // TC01: the closest point is in the middle of the list
        assertEquals(p1, r.findClosestPoint(points01), "find closest point is incorrect");

        // =============== Boundary Values Tests ==================
        //TC11: empty list
        assertNull(r.findClosestPoint(p0), "find closest point is incorrect");

        //TC12: the closest point is the first point in the list
        List<Point> points11 = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27);
        assertEquals(p1, r.findClosestPoint(points11), "find closest point is incorrect");

        //TC13: the closest point is the last point in the list
        List<Point> points12 = List.of(p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p1);
        assertEquals(p1, r.findClosestPoint(points11), "find closest point is incorrect");


    }
}