package net.dinkla.raytracerchallenge.math

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class TupleTest {

    private val p = point(1, 2, 3)
    private val v = vector(1, 2, 3)

    @Test
    fun `adding to points is impossible`() {
        assertThrows(AssertionError::class.java, { p + p })
    }

    @Test
    fun `subtracting a point from a vector is impossible`() {
        assertThrows(AssertionError::class.java, { v - p })
    }

    @Test
    fun `dot needs vectors`() {
        assertThrows(AssertionError::class.java, { v dot p })
        assertThrows(AssertionError::class.java, { p dot v })
        assertThrows(AssertionError::class.java, { p dot p })
    }

    @Test
    fun `cross needs vectors`() {
        assertThrows(AssertionError::class.java, { v cross p })
        assertThrows(AssertionError::class.java, { p cross v })
        assertThrows(AssertionError::class.java, { p cross p })
    }

    @Test
    fun `magnitude needs vector`() {
        assertThrows(AssertionError::class.java, { p.magnitude() })
    }

}