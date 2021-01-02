package net.dinkla.raytracerchallenge.patterns

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.vector
import kotlin.math.floor

class RingPattern(val a: Color, val  b: Color) : Pattern() {

    override fun atInPatternSpace(point: Point): Color {
        val len = vector(point.x, 0.0, point.z).magnitude()
        return if (floor(len).toInt() % 2 == 0) a else b
    }

}
