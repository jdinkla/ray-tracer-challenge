package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.vector
import kotlin.math.abs
import kotlin.math.max

// Axis-aligned unit cube spanning -1..1 on every axis, intersected with the slab method.
class Cube : Shape() {

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val (xtmin, xtmax) = checkAxis(ray.origin.x, ray.direction.x)
        val (ytmin, ytmax) = checkAxis(ray.origin.y, ray.direction.y)
        val (ztmin, ztmax) = checkAxis(ray.origin.z, ray.direction.z)

        val tmin = maxOf(xtmin, ytmin, ztmin)
        val tmax = minOf(xtmax, ytmax, ztmax)

        if (tmin > tmax) {
            return Intersections()
        }
        return Intersections(isec(tmin), isec(tmax))
    }

    private fun checkAxis(origin: Double, direction: Double): Pair<Double, Double> {
        val tminNumerator = -1.0 - origin
        val tmaxNumerator = 1.0 - origin
        val tmin = tminNumerator / direction
        val tmax = tmaxNumerator / direction
        return if (tmin > tmax) Pair(tmax, tmin) else Pair(tmin, tmax)
    }

    override fun normalInObjectSpace(point: Point): Vector {
        val maxc = max(abs(point.x), max(abs(point.y), abs(point.z)))
        return when (maxc) {
            abs(point.x) -> vector(point.x, 0.0, 0.0)
            abs(point.y) -> vector(0.0, point.y, 0.0)
            else -> vector(0.0, 0.0, point.z)
        }
    }
}
