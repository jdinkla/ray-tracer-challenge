package net.dinkla.raytracerchallenge.math

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import net.dinkla.raytracerchallenge.math.Approx.isDifferenceSmall

class ApproxTest : AnnotationSpec() {
    @Test
    fun `isDifferenceSmall should be true`() {
        isDifferenceSmall(0.2672612419124244, 0.26726) shouldBe true
    }

    @Test
    fun `isDifferenceSmall should be false`() {
        isDifferenceSmall(0.2672612419124244, 0.26725) shouldBe false
    }
}