package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.point
import kotlin.math.sqrt

class Sphere : GeometricObject {

    override fun intersect(ray: Ray): Intersections {
        val sphereToRay = ray.origin - point(0, 0, 0)
        val a = ray.direction dot ray.direction
        val b = 2 * (ray.direction dot sphereToRay)
        val c = (sphereToRay dot sphereToRay) - 1
        val discriminant = b * b - 4 * a * c
        if (discriminant < 0.0) {
            return Intersections()
        }
        val t1 = (-b - sqrt(discriminant)) / (2 * a)
        val t2 = (-b + sqrt(discriminant)) / (2 * a)
        return Intersections(isec(t1), isec(t2))
    }

}