package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK
import net.dinkla.raytracerchallenge.math.Color.Companion.WHITE
import net.dinkla.raytracerchallenge.math.point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StripePatternTest {

    private val bw = StripePattern(BLACK, WHITE)
    private val wb = StripePattern(WHITE, BLACK)

    @Test
    fun `StripePattern works for 0 dot 9`() {
        assertEquals(BLACK, bw.at(point(0.9, 0.0, 0.9)))
        assertEquals(WHITE, wb.at(point(0.9, 0.0, 0.9)))
    }

    @Test
    fun `StripePattern works for 1 dot 1`() {
        assertEquals(WHITE, bw.at(point(1.1, 0.0, 0.9)))
        assertEquals(BLACK, wb.at(point(1.1, 0.0, 0.9)))
    }

    @Test
    fun `StripePattern works for minus 0 dot 1`() {
        assertEquals(WHITE, bw.at(point(-0.1, 0.0, 0.9)))
        assertEquals(BLACK, wb.at(point(-0.1, 0.0, 0.9)))
    }

}