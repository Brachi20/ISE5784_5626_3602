package primitives;
/**
 * * Unit tests for primitives.Point class
 * * @author Hen Elkayam ,Barchi Tarkieltaub
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    /**
     * Test method for
     * {@link primitives.Point#subtract(Point)}
     */
    @Test
    void testSubtract() {
        Vector v1=new Vector(1,2,3);
        Vector v2=new Vector(-1,-2,-3);
        // ============ Equivalence Partitions Tests ==============

        // TC01: test unexpected exception from subtract of two different points
        assertEquals(new Vector(2,4,6),v1.subtract(v2),
                "subtract(): Point - Point does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from subtract of point and itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "subtract() Point - itself does not throw an exception ");
        // TC12: test wrong exception from subtract of two different points
        assertThrows(Exception.class, () -> v1.subtract(v2),
                "subtract() Point - Point throw wrong exception");
    }

    /**
     * Test method for {@link primitives.Point add(primitives.Point)}.
     */
    @Test
    void testAdd() {
        Vector v1=new Vector(1,2,3);
        Point p1=new Point(4d,5d,2d);
        Point p2=new Vector(-1,-2,-3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test result of add vector to point
        assertEquals(new Point(5d,7d,5d),p1.add(v1),
                "add(): (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: test center point from add of vector and point
        assertEquals(new Point(0d,0d,0d),p2.add(v1),
                "add(): (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        Point p1=new Point(1d,5d,2d);
        Point p2=new Point(0d,3d,0d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: test result from distance of point to point
        assertEquals(9d ,p1.distanceSquared(p2),
                "distanceSquared(): squared distance between points is wrong");
        // TC02: test result from distance of point to point
        assertEquals(9d ,p2.distanceSquared(p1),
                "distanceSquared(): squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: test zero result from distanceSquared of point to itself
        assertEquals(0d ,p1.distanceSquared(p1),
                "distanceSquared(): point squared distance to itself is not zero");

    }

    @Test
    void testDistance() {

        Point p1=new Point(1d,5d,2d);
        Point p2=new Point(0d,3d,0d);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test result from distance of point to point
        assertEquals(3d ,p1.distance(p2),
                "distance(): distance between points is wrong");
        // TC02: test result from distance of point to point
        assertEquals(3d ,p2.distance(p1),
                "distance(): distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TC11: test zero result from distance of point to itself
        assertEquals(0d ,p1.distance(p1),
                "distance(): point distance to itself is not zero");
    }
}