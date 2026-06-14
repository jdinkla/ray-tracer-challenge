package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.objects.Shape
import kotlin.math.pow
import kotlin.math.sqrt

@Suppress("UnusedPrivateMember", "LongParameterList")
class Computations(
    t: Double,
    `object`: Shape,
    val point: Point,
    val eyeV: Vector,
    val normalV: Vector,
    val reflectV: Vector = Vector.ORIGIN,
    val inside: Boolean = false,
    val n1: Double = 1.0,
    val n2: Double = 1.0
) : Intersection(t, `object`) {

    val overPoint: Point = point + normalV * EPSILON
    val underPoint: Point = point - normalV * EPSILON

    companion object {
        fun prepare(hit: Intersection, ray: Ray, xs: Intersections = Intersections(hit)): Computations {
            val point = ray.position(hit.t)
            val eyeV = -ray.direction
            val rawNormal = hit.`object`.normal(point, hit)
            val inside = rawNormal dot eyeV < 0.0
            val normalV = if (inside) -rawNormal else rawNormal
            val reflectV = ray.direction.reflect(normalV)
            val (n1, n2) = refractiveIndices(hit, xs)
            return Computations(hit.t, hit.`object`, point, eyeV, normalV, reflectV, inside, n1, n2)
        }

        // Walks the sorted intersections, tracking which transparent objects the ray is inside,
        // to find the refractive indices on either side of the hit (n1 = exited, n2 = entered).
        private fun refractiveIndices(hit: Intersection, xs: Intersections): Pair<Double, Double> {
            val containers = mutableListOf<Shape>()
            var n1 = 1.0
            var n2 = 1.0
            for (idx in 0 until xs.count()) {
                val intersection = xs[idx]
                if (intersection === hit) {
                    n1 = if (containers.isEmpty()) 1.0 else containers.last().material.refractiveIndex
                }
                val existing = containers.indexOfFirst { it === intersection.`object` }
                if (existing >= 0) {
                    containers.removeAt(existing)
                } else {
                    containers.add(intersection.`object`)
                }
                if (intersection === hit) {
                    n2 = if (containers.isEmpty()) 1.0 else containers.last().material.refractiveIndex
                    break
                }
            }
            return Pair(n1, n2)
        }
    }
}

// Schlick approximation of the Fresnel reflectance at the hit point: the fraction of light reflected
// (vs. refracted) given the eye angle and the refractive indices on each side of the boundary.
fun schlick(comps: Computations): Double {
    var cos = comps.eyeV dot comps.normalV
    if (comps.n1 > comps.n2) {
        val ratio = comps.n1 / comps.n2
        val sin2t = ratio * ratio * (1.0 - cos * cos)
        if (sin2t > 1.0) {
            return 1.0
        }
        cos = sqrt(1.0 - sin2t)
    }
    val r0 = ((comps.n1 - comps.n2) / (comps.n1 + comps.n2)).pow(2)
    return r0 + (1.0 - r0) * (1.0 - cos).pow(5)
}
