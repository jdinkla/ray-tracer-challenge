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

// Cylinder of radius 1 around the y-axis, optionally truncated to [minimum, maximum] and capped.
class Cylinder : Shape() {

    var minimum: Double = Double.NEGATIVE_INFINITY
    var maximum: Double = Double.POSITIVE_INFINITY
    var closed: Boolean = false

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val xs = mutableListOf<Intersection>()
        val a = ray.direction.x * ray.direction.x + ray.direction.z * ray.direction.z
        if (abs(a) >= EPSILON) {
            val b = 2.0 * (ray.origin.x * ray.direction.x + ray.origin.z * ray.direction.z)
            val c = ray.origin.x * ray.origin.x + ray.origin.z * ray.origin.z - 1.0
            val disc = b * b - 4.0 * a * c
            if (disc < 0.0) {
                return Intersections()
            }
            val sqrtDisc = sqrt(disc)
            val t0 = minOf((-b - sqrtDisc) / (2.0 * a), (-b + sqrtDisc) / (2.0 * a))
            val t1 = maxOf((-b - sqrtDisc) / (2.0 * a), (-b + sqrtDisc) / (2.0 * a))
            val y0 = ray.origin.y + t0 * ray.direction.y
            if (minimum < y0 && y0 < maximum) {
                xs.add(isec(t0))
            }
            val y1 = ray.origin.y + t1 * ray.direction.y
            if (minimum < y1 && y1 < maximum) {
                xs.add(isec(t1))
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
        if (checkCap(ray, tLower)) {
            xs.add(isec(tLower))
        }
        val tUpper = (maximum - ray.origin.y) / ray.direction.y
        if (checkCap(ray, tUpper)) {
            xs.add(isec(tUpper))
        }
    }

    private fun checkCap(ray: Ray, t: Double): Boolean {
        val x = ray.origin.x + t * ray.direction.x
        val z = ray.origin.z + t * ray.direction.z
        return x * x + z * z <= 1.0
    }

    override fun normalInObjectSpace(point: Point): Vector {
        val dist = point.x * point.x + point.z * point.z
        return when {
            dist < 1.0 && point.y >= maximum - EPSILON -> vector(0.0, 1.0, 0.0)
            dist < 1.0 && point.y <= minimum + EPSILON -> vector(0.0, -1.0, 0.0)
            else -> vector(point.x, 0.0, point.z)
        }
    }
}
