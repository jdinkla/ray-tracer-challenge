package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

class Plane : Shape() {

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val t = (- ray.origin.y) / ray.direction.y
        if (t.isFinite()) {
            return Intersections(isec(t))
        }
        return Intersections()
    }

    override fun normalInObjectSpace(point: Point): Vector = Vector.UP

}