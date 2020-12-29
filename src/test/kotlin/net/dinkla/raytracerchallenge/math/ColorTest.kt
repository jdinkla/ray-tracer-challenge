package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK
import net.dinkla.raytracerchallenge.math.Color.Companion.WHITE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ColorTest {

    @Test
    fun `toByte() works for BLACK`() {
        assertEquals(Triple(0, 0, 0), BLACK.toInt())
    }

    @Test
    fun `toByte() works for WHITE`() {
        assertEquals(Triple(255, 255, 255), WHITE.toInt())
    }

}