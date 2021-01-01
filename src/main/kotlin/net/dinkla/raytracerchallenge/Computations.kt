package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.objects.GeometricObject

@Suppress("UnusedPrivateMember")
class Computations(
    t: Double,
    `object`: GeometricObject,
    val point: Point,
    val eyeV: Vector,
    val normalV: Vector
) : Intersection(t, `object`) {

    companion object {
        fun prepare(i: Intersection, ray: Ray): Computations {
            val point = ray.position(i.t)
            val eyeV = -ray.direction
            val normalV = i.`object`.normal(point)
            return Computations(i.t, i.`object`, point, eyeV, normalV)
        }
    }
}