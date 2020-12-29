package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.approx
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs

internal class ApproxTest {

    @Test
    fun `approx is true`() {
        assertTrue(approx(0.2672612419124244, 0.26726))
    }

    @Test
    fun `approx is false`() {
        assertFalse(approx(0.2672612419124244, 0.26725))
    }

}