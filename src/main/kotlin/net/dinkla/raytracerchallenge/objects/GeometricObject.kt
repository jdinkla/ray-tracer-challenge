package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray

interface GeometricObject {

    fun intersect(ray: Ray): Intersections

    fun isec(t: Double) = Intersection(t, this)
}