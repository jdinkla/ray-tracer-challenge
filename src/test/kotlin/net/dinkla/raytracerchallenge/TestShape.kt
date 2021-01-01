package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.objects.Shape

class TestShape : Shape() {

    lateinit var ray: Ray
    lateinit var point: Point

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        this.ray = ray
        return Intersections()
    }

    override fun normalInObjectSpace(point: Point): Vector {
        this.point = point
        return point.toVector()
    }

}