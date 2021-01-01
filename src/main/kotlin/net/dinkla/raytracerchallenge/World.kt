package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.GeometricObject
import net.dinkla.raytracerchallenge.objects.Sphere

class World {

    val objects: MutableList<GeometricObject> = mutableListOf()
    var light: PointLight = PointLight()

    fun intersect(ray: Ray): Intersections {
        val xss = objects.map { it.intersect(ray) }
        return Intersections.combine(xss)
    }

    fun shadeHit(comps: Computations): Color = lighting(light, comps)

    fun colorAt(ray: Ray): Color {
        val xs = intersect(ray)
        val hit = xs.hit()
        if (hit == null) {
            return Color.BLACK
        } else {
            val comps = Computations.prepare(hit, ray)
            return shadeHit(comps)
        }
    }

    fun render(c: Camera): Canvas {
        val canvas = Canvas(c.hSize, c.vSize)
        canvas.loop { x: Int, y: Int ->
            val ray = c.rayForPixel(x, y)
            colorAt(ray)
        }
        return canvas
    }

    companion object {
        fun defaultWorld(): World {
            val w = World()
            val position = point(-10, 10, -10)
            val intensity = Color.WHITE
            w.light = PointLight(position, intensity)

            val s1 = Sphere()
            s1.material.color = Color(0.8, 1.0, 0.6)
            s1.material.diffuse = 0.7
            s1.material.specular = 0.2
            w.objects.add(s1)

            val s2 = Sphere()
            s2.transform = scaling(0.5, 0.5, 0.5)
            w.objects.add(s2)

            return w
        }
    }
}