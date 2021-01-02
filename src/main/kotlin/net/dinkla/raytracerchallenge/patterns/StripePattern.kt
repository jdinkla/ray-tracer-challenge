package net.dinkla.raytracerchallenge.patterns

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import kotlin.math.floor

data class StripePattern(val a: Color, val b: Color) : Pattern() {

    override fun atInPatternSpace(point: Point): Color = if (floor(point.x).toInt() % 2 == 0) a else b

}
