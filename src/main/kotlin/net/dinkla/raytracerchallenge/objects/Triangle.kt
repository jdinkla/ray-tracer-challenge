package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import java.util.Objects
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
        return Intersections(intersectionAt(t, u, v))
    }

    // Hook so SmoothTriangle can record the barycentric u/v on the intersection.
    protected open fun intersectionAt(t: Double, u: Double, v: Double): Intersection = isec(t)

    override fun normalInObjectSpace(point: Point): Vector = normal

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        other as Triangle
        return p1 == other.p1 && p2 == other.p2 && p3 == other.p3
    }

    override fun hashCode(): Int = Objects.hash(p1, p2, p3)
}
