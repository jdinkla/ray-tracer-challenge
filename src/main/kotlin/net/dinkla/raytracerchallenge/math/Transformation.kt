package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity
import kotlin.math.cos
import kotlin.math.sin

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

    fun rotationX(d: Double) : Matrix {
        val m = identity(4)
        m[1, 1] = cos(d)
        m[1, 2] = - sin(d)
        m[2, 1] = sin(d)
        m[2, 2] = cos(d)
        return m
    }

    fun rotationY(d: Double) : Matrix {
        val m = identity(4)
        m[0, 0] = cos(d)
        m[0, 2] = sin(d)
        m[2, 0] = - sin(d)
        m[2, 2] = cos(d)
        return m
    }

    fun rotationZ(d: Double) : Matrix {
        val m = identity(4)
        m[0, 0] = cos(d)
        m[0, 1] = - sin(d)
        m[1, 0] = sin(d)
        m[1, 1] = cos(d)
        return m
    }

    fun shearing(xy: Double, xz: Double, yx: Double, yz: Double, zx: Double, zy: Double): Matrix {
        val m = identity(4)
        m[0, 1] = xy
        m[0, 2] = xz
        m[1, 0] = yx
        m[1, 2] = yz
        m[2, 0] = zx
        m[2, 1] = zy
        return m
    }

}