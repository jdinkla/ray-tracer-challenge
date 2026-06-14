package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.vector
import kotlin.math.abs
import kotlin.math.sqrt

// Double-napped cone around the y-axis (radius == |y|), optionally truncated and capped.
class Cone : Shape() {

    var minimum: Double = Double.NEGATIVE_INFINITY
    var maximum: Double = Double.POSITIVE_INFINITY
    var closed: Boolean = false

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val xs = mutableListOf<Intersection>()
        val d = ray.direction
        val o = ray.origin
        val a = d.x * d.x - d.y * d.y + d.z * d.z
        val b = 2.0 * o.x * d.x - 2.0 * o.y * d.y + 2.0 * o.z * d.z
        val c = o.x * o.x - o.y * o.y + o.z * o.z

        if (abs(a) < EPSILON) {
            if (abs(b) >= EPSILON) {
                xs.add(isec(-c / (2.0 * b)))
            }
        } else {
            val disc = b * b - 4.0 * a * c
            if (disc >= 0.0) {
                val sqrtDisc = sqrt(disc)
                val t0 = minOf((-b - sqrtDisc) / (2.0 * a), (-b + sqrtDisc) / (2.0 * a))
                val t1 = maxOf((-b - sqrtDisc) / (2.0 * a), (-b + sqrtDisc) / (2.0 * a))
                val y0 = o.y + t0 * d.y
                if (minimum < y0 && y0 < maximum) {
                    xs.add(isec(t0))
                }
                val y1 = o.y + t1 * d.y
                if (minimum < y1 && y1 < maximum) {
                    xs.add(isec(t1))
                }
            }
        }
        intersectCaps(ray, xs)
        return Intersections(*xs.toTypedArray())
    }

    private fun intersectCaps(ray: Ray, xs: MutableList<Intersection>) {
        if (!closed || abs(ray.direction.y) < EPSILON) {
            return
        }
        val tLower = (minimum - ray.origin.y) / ray.direction.y
        if (checkCap(ray, tLower, abs(minimum))) {
            xs.add(isec(tLower))
        }
        val tUpper = (maximum - ray.origin.y) / ray.direction.y
        if (checkCap(ray, tUpper, abs(maximum))) {
            xs.add(isec(tUpper))
        }
    }

    private fun checkCap(ray: Ray, t: Double, radius: Double): Boolean {
        val x = ray.origin.x + t * ray.direction.x
        val z = ray.origin.z + t * ray.direction.z
        return x * x + z * z <= radius * radius
    }

    override fun normalInObjectSpace(point: Point): Vector {
        val dist = point.x * point.x + point.z * point.z
        return when {
            dist < maximum * maximum && point.y >= maximum - EPSILON -> vector(0.0, 1.0, 0.0)
            dist < minimum * minimum && point.y <= minimum + EPSILON -> vector(0.0, -1.0, 0.0)
            else -> {
                val y = if (point.y > 0.0) -sqrt(dist) else sqrt(dist)
                vector(point.x, y, point.z)
            }
        }
    }
}
