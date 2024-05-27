package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /** test method for {@link geometries.Sphere #Sphere(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test building some sphere
        Sphere s=new Sphere(new Point(1,0,0),3d);
        assertNotNull(s, "Failed constructing a correct Sphere");

    }

    /** Test method for {@link geometries.Sphere#getNormal(Point)}. */

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test calculate normal

        Sphere s=new Sphere(new Point(1,0,0),3d);
        assertEquals(new Vector(1,0,0),
                s.getNormal(new Point(4,0,0)),
                "normal of Sphere is incorrect");

    }
}