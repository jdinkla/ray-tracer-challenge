package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.approx
import kotlin.math.sqrt

data class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {
    fun isPoint(): Boolean = w == 1.0
    fun isVector(): Boolean  = w == 0.0

    operator fun plus(t: Tuple) = Tuple(x + t.x, y + t.y, z + t.z,w + t.w)
    operator fun minus(t: Tuple) = Tuple(x - t.x, y - t.y, z - t.z,w - t.w)
    operator fun unaryMinus() = Tuple(-x, -y, -z, -w)

    operator fun times(s: Double) = Tuple(x * s, y * s, z * s, w * s)
    operator fun div(s: Double) = Tuple(x / s, y / s, z / s, w / s)

    infix fun dot(t: Tuple): Double {
        assert(isVector())
        assert(t.isVector())
        return x * t.x + y * t.y + z * t.z // + w * t.w
    }

    infix fun cross(t: Tuple): Tuple {
        assert(isVector())
        assert(t.isVector())
        return vector(y * t.z - z * t.y, z * t.x - x * t.z, x * t.y - y * t.x)
    }

    fun magnitude(): Double {
        assert(isVector())
        fun sqrLength(): Double = x * x + y * y + z * z // + w * w  w is 0.0 for vector
        return sqrt(sqrLength())
    }

    fun normalize(): Tuple {
        val l = magnitude()
        return vector(x / l, y / l, z / l)
    }

    override fun equals(other: Any?): Boolean {
        val p: Tuple = other as? Tuple ?: return false
        return approx(x, p.x) && approx(y, p.y) && approx(z, p.z) && w == p.w
    }

    fun reflect(n: Tuple): Tuple {
        assert(false)
        return n
    }
}

fun tuple(x: Int, y: Int, z: Int, w: Int) = Tuple(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

fun point(x: Int, y: Int, z: Int) = Tuple(x.toDouble(), y.toDouble(), z.toDouble(), 1.0)
// fun point(x: Double, y: Double, z: Double) = tuple(x, y, z, 1.0)

fun vector(x: Int, y: Int, z: Int) = Tuple(x.toDouble(), y.toDouble(), z.toDouble(), 0.0)
fun vector(x: Double, y: Double, z: Double) = Tuple(x, y, z, 0.0)
