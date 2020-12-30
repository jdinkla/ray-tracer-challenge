package net.dinkla.raytracerchallenge.math

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class MatrixTest {

    @Test
    fun `n at least 2`() {
        assertThrows(AssertionError::class.java, { Matrix(1) })
    }

}