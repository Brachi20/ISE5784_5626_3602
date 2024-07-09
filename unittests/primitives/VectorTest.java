package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class VectorTest {
    /**
     * Test method for
     * {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        //TC01:test calculate add two point
        Vector v2 = new Vector(-4, -2, 0);
        Vector v3 = new Vector(2, 3, 5);
        assertEquals(new Vector(-2, 1, 5), v2.add(v3), "add(): does not work correctly");

        // =============== Boundary Values Tests ==================
        Vector v1 = new Vector(4, 2, 0);
        // TC11: test zero vector from add of Equal and opposite vectors
        assertThrows(IllegalArgumentException.class, () -> v1.add(v2),
                "add(): Vector + -itself does not throw an exception");

    }

    /**
     * Test method for
     * {@link primitives.Vector testScale(primitives.Vector)}.
     */

    @Test
    void testScale() {
        Vector v1 = new Vector(1, 4, 8);

        // ============ Equivalence Partitions Tests ==============
        //TC01:test scale vector with scalar
        assertEquals(new Vector(2, 8, 16), v1.scale(2), "scale(): scale vector with scalar does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from scale of Vector with zero point
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "scale():cant scale vector with zero");
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(4, 1.5, 1);
        Vector v2 = new Vector(2, -4, -2);
        Vector v3 = new Vector(1, 1, 1);
        Vector v4 = new Vector(2, 5, 4);
        // ============ Equivalence Partitions Tests ==============
        //TC01:Test that the dotProduct work correctly
        assertEquals(-24, v2.dotProduct(v4), "dotProduct(): Wrong value");
        // =============== Boundary Values Tests ==================
        // TC11: test for return zero from scale of orthogonal vectors
        assertEquals(0, v1.dotProduct(v2), "(): scalar multiplication between orthogonal vectors is not zero");
        //TC12:test return sum of coordinates of vector from dotProduct of vector and unit vector
        assertEquals(11, v3.dotProduct(v4),
                "dotProduct(): Scalar multiplication between a vector and a unit vector does not work correctly");


    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(4, 1.5, 1);
        Vector v2 = new Vector(2, -4, -2);
        Vector v3 = new Vector(8, 3, 2);

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

    /**
     * Test method for
     * {@link primitives.Vector lengthSquared(primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(2, 5, 4);
        // ============ Equivalence Partitions Tests ==============
        //TC01:Test that the lengthSquared work correctly
        assertEquals(45, v1.lengthSquared(), "lengthSquared():wrong value");


    }

    /**
     * Test method for
     * {@link primitives.Vector normealize(primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        Vector v1 = new Vector(10, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        //TC01:Test that the normalize work correctly
        assertEquals(new Vector(1, 0, 0), v1.normalize(),
                "normalize(): The normalization operation does not work correctly");
    }
}