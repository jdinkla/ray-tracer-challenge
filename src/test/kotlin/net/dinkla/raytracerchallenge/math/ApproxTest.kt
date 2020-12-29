package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.compare
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ApproxTest {

    @Test
    fun `approx is true`() {
        assertTrue(compare(0.2672612419124244, 0.26726))
    }

    @Test
    fun `approx is false`() {
        assertFalse(compare(0.2672612419124244, 0.26725))
    }

}