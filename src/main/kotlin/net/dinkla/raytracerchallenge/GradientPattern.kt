package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import kotlin.math.floor

class GradientPattern(val a: Color, val b: Color) : Pattern() {

    private val distance = b - a

    override fun atInPatternSpace(point: Point): Color {
//        val d = abs(decimalPlaces(point.x))
//        return a * (1.0 - d) + b * d
        val fraction = point.x - floor(point.x)
        return a + distance * fraction
    }

}

//private fun decimalPlaces(d: Double): Double = d - d.toInt().toDouble()
