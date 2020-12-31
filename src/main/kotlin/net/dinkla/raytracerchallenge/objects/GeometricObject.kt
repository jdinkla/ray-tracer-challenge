package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

abstract class GeometricObject {

    var material: Material = Material()

    abstract fun intersect(ray: Ray): Intersections
    abstract fun normal(p: Point): Vector

    fun isec(t: Double) = Intersection(t, this)
}