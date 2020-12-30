package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.isDifferenceSmall
import java.util.Objects
import kotlin.math.sqrt

typealias Vector = Tuple
typealias Point = Tuple

data class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {
    fun isPoint(): Boolean = w == 1.0
    fun isVector(): Boolean  = w == 0.0

    operator fun plus(t: Tuple): Tuple {
        assert(w + t.w <= 1.0)
        return Tuple(x + t.x, y + t.y, z + t.z,w + t.w)
    }
    operator fun minus(t: Tuple): Tuple {
        assert(w - t.w >= 0.0)
        return Tuple(x - t.x, y - t.y, z - t.z,w - t.w)
    }
    operator fun unaryMinus() = Tuple(-x, -y, -z, -w)

    operator fun times(s: Double) = Tuple(x * s, y * s, z * s, w * s)
    operator fun div(s: Double) = Tuple(x / s, y / s, z / s, w / s)

    infix fun dot(v: Vector): Double {
        assert(isVector())
        assert(v.isVector())
        return x * v.x + y * v.y + z * v.z // + w * t.w
    }

    infix fun cross(v: Vector): Vector {
        assert(isVector())
        assert(v.isVector())
        return vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
    }

    fun magnitude(): Double {
        assert(isVector())
        fun sqrLength(): Double = x * x + y * y + z * z // + w * w  w is 0.0 for vector
        return sqrt(sqrLength())
    }

    fun normalize(): Vector {
        val l = magnitude()
        return vector(x / l, y / l, z / l)
    }

    override fun equals(other: Any?): Boolean {
        val p: Tuple = other as? Tuple ?: return false
        return isDifferenceSmall(x, p.x) && isDifferenceSmall(y, p.y) && isDifferenceSmall(z, p.z) && w == p.w
    }

    override fun hashCode(): Int = Objects.hash(x, y, z, w)

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
