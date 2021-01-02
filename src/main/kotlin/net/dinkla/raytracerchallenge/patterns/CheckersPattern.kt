package net.dinkla.raytracerchallenge.patterns

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import kotlin.math.floor

class CheckersPattern(val a: Color, val b: Color) : Pattern() {
    override fun atInPatternSpace(point: Point): Color {
        val sum = floor(point.x) + floor(point.y) + floor(point.z)
        return if (sum.toInt() % 2 == 0) a else b
    }
}
