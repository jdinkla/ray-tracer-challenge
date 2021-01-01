package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Matrix.Companion.matrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class MatrixTest {

    @Test
    fun `n at least 2`() {
        assertThrows(AssertionError::class.java) { Matrix(1) }
    }

    @Test
    fun `can't change identity4 matrix`() {
        val m = identity4
        assertThrows(AssertionError::class.java) { m[1, 1] = 2.0 }
    }

    @Test
    fun `create 2x2 matrix`() {
        val m = matrix(1.1, 2.2, 3.3, 4.4)
        assertEquals(1.1, m[0, 0], EPSILON)
        assertEquals(4.4, m[1, 1], EPSILON)
    }

    @Test
    fun `need four elements for a 2x2 matrix`() {
        assertThrows(AssertionError::class.java) { matrix(1.1, 2.2, 3.3) }
    }

}