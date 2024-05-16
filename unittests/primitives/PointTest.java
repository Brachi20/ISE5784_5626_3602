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

        // TC01: test unexpected exception from subtract of two different vectors
        assertEquals(new Vector(2,4,6),v1.subtract(v2),
                "subtract(): Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from subtract of vector and itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "subtract() Vector - itself does not throw an exception ");
        // TC11: test worng exception from subtract of two different vectors
        assertThrows(Exception.class, () -> v1.subtract(v2),
                "subtract() Vector - Vector throw worng exception ");
    }

    /**
     * Test method for {@link primitives.Point add(primitives.Point)}.
     */
    @Test
    void testAdd() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
    }
}