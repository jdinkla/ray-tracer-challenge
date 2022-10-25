package net.dinkla.raytracerchallenge.math

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK
import net.dinkla.raytracerchallenge.math.Color.Companion.WHITE

internal class ColorTest : AnnotationSpec() {

    @Test
    fun `toRGB() works for BLACK`() {
        BLACK.toRGB() shouldBe Triple(0, 0, 0)
    }

    @Test
    fun `toRGB() works for WHITE`() {
        WHITE.toRGB() shouldBe Triple(255, 255, 255)
    }

    @Test
    fun `fromString()`() {
        Color.fromString("35281E") shouldBe Color(53.0 / 255.0, 40.0 / 255.0, 30.0 / 255.0)
    }
}