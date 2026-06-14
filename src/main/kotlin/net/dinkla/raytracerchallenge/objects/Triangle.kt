package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import kotlin.math.abs

// Flat triangle defined by three vertices, intersected with the Möller–Trumbore algorithm.
open class Triangle(val p1: Point, val p2: Point, val p3: Point) : Shape() {

    val e1: Vector = p2 - p1
    val e2: Vector = p3 - p1
    val normal: Vector = (e2 cross e1).normalize()

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val dirCrossE2 = ray.direction cross e2
        val det = e1 dot dirCrossE2
        if (abs(det) < EPSILON) {
            return Intersections()
        }
        val f = 1.0 / det
        val p1ToOrigin = ray.origin - p1
        val u = f * (p1ToOrigin dot dirCrossE2)
        if (u < 0.0 || u > 1.0) {
            return Intersections()
        }
        val originCrossE1 = p1ToOrigin cross e1
        val v = f * (ray.direction dot originCrossE1)
        if (v < 0.0 || u + v > 1.0) {
            return Intersections()
        }
        val t = f * (e2 dot originCrossE1)
        return Intersections(isec(t))
    }

    override fun normalInObjectSpace(point: Point): Vector = normal
}
