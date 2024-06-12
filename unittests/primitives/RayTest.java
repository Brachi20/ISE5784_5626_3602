package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: t is positive
        Ray r=new Ray(new Point(1,0,0),new Vector(0,0,1));
        assertEquals(new Point(1,0,2),r.getPoint(2), "get point is incorrect");

        // TC02: t is negative
        Ray r1=new Ray(new Point(1,0,0),new Vector(0,0,1));
        assertEquals(new Point(1,0,-2),r.getPoint(-2), "get point is incorrect");

        //============ Boundary Values Tests ==================
        // TC03: t is zero
        Ray r2=new Ray(new Point(1,0,0),new Vector(0,0,1));
        assertEquals(new Point(1,0,0),r.getPoint(0), "get point is incorrect");
    }
}