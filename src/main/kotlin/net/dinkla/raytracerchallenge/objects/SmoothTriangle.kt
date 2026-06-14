package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import java.util.Objects

// Triangle with per-vertex normals, interpolated across the face by the hit's barycentric u/v for
// smooth (Phong) shading.
class SmoothTriangle(
    p1: Point,
    p2: Point,
    p3: Point,
    val n1: Vector,
    val n2: Vector,
    val n3: Vector,
) : Triangle(p1, p2, p3) {

    override fun intersectionAt(t: Double, u: Double, v: Double): Intersection = Intersection(t, this, u, v)

    override fun normalInObjectSpace(point: Point, hit: Intersection?): Vector {
        val h = requireNotNull(hit) { "a smooth triangle needs the hit's u/v to interpolate its normal" }
        return n2 * h.u + n3 * h.v + n1 * (1.0 - h.u - h.v)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        other as SmoothTriangle
        return p1 == other.p1 && p2 == other.p2 && p3 == other.p3 &&
            n1 == other.n1 && n2 == other.n2 && n3 == other.n3
    }

    override fun hashCode(): Int = Objects.hash(p1, p2, p3, n1, n2, n3)
}
