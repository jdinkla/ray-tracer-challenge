package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity

object Transformation {

    fun translation(x: Double, y: Double, z: Double): Matrix {
        val m = identity(4)
        m[0, 3] = x
        m[1, 3] = y
        m[2, 3] = z
        return m
    }

    fun translation(x: Int, y: Int, z: Int): Matrix = translation(x.toDouble(), y.toDouble(), z.toDouble())

    fun scaling(x: Double, y: Double, z: Double): Matrix {
        val m = identity(4)
        m[0, 0] = x
        m[1, 1] = y
        m[2, 2] = z
        return m
    }

    fun scaling(x: Int, y: Int, z: Int): Matrix = scaling(x.toDouble(), y.toDouble(), z.toDouble())

}