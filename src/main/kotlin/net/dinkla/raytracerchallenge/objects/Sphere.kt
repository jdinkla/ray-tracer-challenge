package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import java.util.Objects
import kotlin.math.sqrt

class Sphere : GeometricObject() {

    var transform: Matrix = Matrix.identity4
        set(t) {
            field = t
            inverse = t.inverse()
            inverseTranspose = inverse.transpose()
        }

    private var inverse: Matrix = Matrix.identity4
    private var inverseTranspose: Matrix = Matrix.identity4

    override fun intersect(ray: Ray): Intersections {
        val objectRay = ray.transform(inverse)
        val toRayOrigin = objectRay.origin.toVector()
        val a = objectRay.direction dot objectRay.direction
        val b = 2 * (objectRay.direction dot toRayOrigin)
        val c = (toRayOrigin dot toRayOrigin) - 1
        val discriminant = b * b - 4 * a * c
        if (discriminant < 0.0) {
            return Intersections()
        }
        val t1 = (-b - sqrt(discriminant)) / (2 * a)
        val t2 = (-b + sqrt(discriminant)) / (2 * a)
        return Intersections(isec(t1), isec(t2))
    }

    override fun normal(p: Point): Vector {
        val objectNormal = (inverse * p).toVector()
        val worldNormal = (inverseTranspose * objectNormal).toVector()
        return worldNormal.normalize()
    }

    override fun equals(other: Any?): Boolean {
        if (null == other || other !is Sphere) {
            return false
        } else {
            return material == other.material && transform == other.transform
        }
    }

    override fun hashCode(): Int = Objects.hash(material, transform)

}