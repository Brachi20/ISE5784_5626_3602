package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class VectorTest {
    /**
     * Test method for
     * {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // =============== Boundary Values Tests ==================
        Vector v1=new Vector(4,2,0);
        Vector v2=new Vector(-4,-2,0);
        Vector v3=new Vector(2,3,5);
        // TC11: test zero vector from add of Equal and opposite vectors
        assertThrows(IllegalArgumentException.class, () -> v1.add(v2),
                "add(): Vector + -itself does not throw an exception");
        // ============ Equivalence Partitions Tests ==============
        // TC12: test f
        assertEquals(new Vector(-2,1,5),v2.add(v3),"add(): does not work correctly");
        assertThrows(Exception.class, () -> v1.add(v3),
                "add(): Vector + Vector throws wrong exception");


    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1=new Vector(4,1.5,1);
        Vector v2=new Vector(2,-4,-2);
        Vector v3=new Vector(8,3,2);

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.0001,
                "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v2), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
    }
}