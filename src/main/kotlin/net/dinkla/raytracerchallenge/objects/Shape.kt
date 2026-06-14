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

    var transform: Matrix = Matrix.identity4

    // Enclosing group/CSG, if any. Drives world<->object space conversion for nested shapes.
    var parent: Shape? = null

    abstract fun intersectInObjectSpace(ray: Ray): Intersections

    abstract fun normalInObjectSpace(point: Point): Vector

    fun intersect(ray: Ray): Intersections {
        val objectRay = ray.transform(transform.inverse())
        return intersectInObjectSpace(objectRay)
    }

    fun normal(point: Point): Vector {
        val objectPoint = worldToObject(point)
        val objectNormal = normalInObjectSpace(objectPoint)
        return normalToWorld(objectNormal)
    }

    // Transforms a world-space point into this shape's object space, walking up through any parents.
    fun worldToObject(point: Point): Point {
        val parentPoint = parent?.worldToObject(point) ?: point
        return transform.inverse() * parentPoint
    }

    // Transforms an object-space normal back into world space, walking up through any parents.
    fun normalToWorld(normal: Vector): Vector {
        val worldNormal = (transform.inverseTranspose() * normal).toVector().normalize()
        return parent?.normalToWorld(worldNormal) ?: worldNormal
    }

    fun isec(t: Double) = Intersection(t, this)
}