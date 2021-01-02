package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import kotlin.math.floor

data class StripePattern(val a: Color, val b: Color) : Pattern {

    fun at(point: Point): Color = if (floor(point.x).toInt() % 2 == 0) a else b

}
