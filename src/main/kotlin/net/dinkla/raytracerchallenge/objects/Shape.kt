package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

abstract class Shape {

    var material: Material = Material()

    open var transform: Matrix = Matrix.identity4
        set(t) {
            field = t
            inverse = t.inverse()
            inverseTranspose = inverse.transpose()
        }

    private var inverse: Matrix = Matrix.identity4
    private var inverseTranspose: Matrix = Matrix.identity4

    protected abstract fun intersectInObjectSpace(ray: Ray): Intersections
    protected abstract fun normalInObjectSpace(point: Point): Vector

    fun intersect(ray: Ray): Intersections {
        val objectRay = ray.transform(inverse)
        return intersectInObjectSpace(objectRay)
    }

    fun normal(point: Point): Vector {
        val objectPoint = inverse * point
        val objectNormal = normalInObjectSpace(objectPoint)
        val worldNormal = (inverseTranspose * objectNormal).toVector()
        return worldNormal.normalize()
    }

    fun isec(t: Double) = Intersection(t, this)
}