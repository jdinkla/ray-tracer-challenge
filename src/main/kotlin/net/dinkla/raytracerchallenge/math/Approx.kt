package net.dinkla.raytracerchallenge.math

import kotlin.math.abs


object Approx {

    val EPSILON: Double = 0.00001

    fun approx(a: Double, b: Double): Boolean = abs(a-b) < EPSILON

}
