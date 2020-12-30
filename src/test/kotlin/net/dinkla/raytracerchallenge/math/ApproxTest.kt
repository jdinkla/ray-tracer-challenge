package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.isDifferenceSmall
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ApproxTest {

    @Test
    fun `approx is true`() {
        assertTrue(isDifferenceSmall(0.2672612419124244, 0.26726))
    }

    @Test
    fun `approx is false`() {
        assertFalse(isDifferenceSmall(0.2672612419124244, 0.26725))
    }

}