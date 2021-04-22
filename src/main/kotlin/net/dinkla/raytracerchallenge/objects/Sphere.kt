package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import java.util.Objects
import kotlin.math.sqrt

class Sphere : Shape() {

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val toRayOrigin = ray.origin.toVector()
        val a = ray.direction dot ray.direction
        val b = 2 * (ray.direction dot toRayOrigin)
        val c = (toRayOrigin dot toRayOrigin) - 1
        val discriminant = b * b - 4 * a * c
        if (discriminant < 0.0) {
            return Intersections()
        }
        val t1 = (-b - sqrt(discriminant)) / (2 * a)
        val t2 = (-b + sqrt(discriminant)) / (2 * a)
        return Intersections(isec(t1), isec(t2))
    }

    override fun normalInObjectSpace(point: Point): Vector = point.toVector()

    override fun equals(other: Any?): Boolean {
        return if (null == other || other !is Sphere) {
            false
        } else {
            material == other.material && transform == other.transform
        }
    }

    override fun hashCode(): Int = Objects.hash(material, transform)

}