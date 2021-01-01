package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.objects.GeometricObject

@Suppress("UnusedPrivateMember")
class Computations(
    t: Double,
    `object`: GeometricObject,
    val point: Point,
    val eyeV: Vector,
    val normalV: Vector,
    val inside: Boolean = false
) : Intersection(t, `object`) {

    val overPoint: Point

    init {
        overPoint = point + normalV * EPSILON
    }

    companion object {
        fun prepare(i: Intersection, ray: Ray): Computations {
            val point = ray.position(i.t)
            val eyeV = -ray.direction
            val normalV = i.`object`.normal(point)
            if (normalV dot eyeV < 0.0) {
                return Computations(i.t, i.`object`, point, eyeV, -normalV, true)
            }
            return Computations(i.t, i.`object`, point, eyeV, normalV, false)
        }
    }
}