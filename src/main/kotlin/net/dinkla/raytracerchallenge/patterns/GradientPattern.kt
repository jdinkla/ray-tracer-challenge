package net.dinkla.raytracerchallenge.patterns

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import kotlin.math.floor

class GradientPattern(val a: Color, val b: Color) : Pattern() {

    private val distance = b - a

    override fun atInPatternSpace(point: Point): Color {
        val fraction = point.x - floor(point.x)
        return a + distance * fraction
    }

}
