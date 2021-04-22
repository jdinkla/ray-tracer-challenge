package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

data class Ray(val origin: Point, val direction: Vector) {
    fun position(d: Double): Point = origin + direction * d

    fun transform(m: Matrix): Ray {
        return Ray(m * origin, m * direction)
    }
}
