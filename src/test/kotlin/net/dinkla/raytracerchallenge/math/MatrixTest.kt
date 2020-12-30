package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
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


}