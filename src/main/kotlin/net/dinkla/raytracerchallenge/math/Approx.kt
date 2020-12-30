package net.dinkla.raytracerchallenge.math

import kotlin.math.abs

object Approx {
    const val EPSILON: Double = 0.00001

    fun isDifferenceSmall(a: Double, b: Double): Boolean = abs(a-b) < EPSILON
}
