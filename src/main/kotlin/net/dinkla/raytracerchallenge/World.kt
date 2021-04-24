package net.dinkla.raytracerchallenge

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dinkla.raytracerchallenge.math.Approx
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere

const val MAX_RECURSION = 5

class World {

    val objects: MutableList<Shape> = mutableListOf()
    var light: PointLight = PointLight()
    private val gridSize = 4

    fun intersect(ray: Ray): Intersections {
        val xss = objects.map { it.intersect(ray) }
        return Intersections.combine(xss)
    }

    fun shadeHit(comps: Computations, remaining: Int = MAX_RECURSION): Color {
        val surface = lighting(light, comps, isShadowed(comps.overPoint))
        val reflected = reflectedColor(comps, remaining)
        return surface + reflected
    }

    fun colorAt(ray: Ray, remaining: Int = MAX_RECURSION): Color {
        val xs = intersect(ray)
        val hit = xs.hit()
        return if (hit == null) {
            Color.BLACK
        } else {
            val comps = Computations.prepare(hit, ray)
            shadeHit(comps, remaining)
        }
    }

    fun render(camera: Camera): Canvas = if (camera.hSize % gridSize == 0 && camera.vSize % gridSize == 0) {
        renderInParallel(camera)
    } else {
        renderSequential(camera)
    }

    fun renderSequential(camera: Camera): Canvas = Canvas(camera.hSize, camera.vSize).apply {
        loop { x: Int, y: Int ->
            val ray = camera.rayForPixel(x, y)
            colorAt(ray)
        }
    }

    fun renderInParallel(camera: Camera): Canvas = Canvas(camera.hSize, camera.vSize).apply {
        runBlocking(Dispatchers.Default) {
            for (y in 0 until height step gridSize) {
                for (x in 0 until width step gridSize) {
                    launch {
                        for (sy in 0 until gridSize) {
                            for (sx in 0 until gridSize) {
                                set(x+sx, y+sy, colorAt(camera.rayForPixel(x+sx, y+sy)))
                            }
                        }
                    }
                }
            }
        }
    }

    fun isShadowed(point: Point): Boolean {
        val v = light.position - point
        val ray = Ray(point, v.normalize())
        val hit = intersect(ray).hit()
        return hit != null && hit.t < v.magnitude()
    }

    fun reflectedColor(comps: Computations, remaining: Int = MAX_RECURSION): Color {
        if (remaining <= 0 || comps.`object`.material.reflective < Approx.EPSILON) {
            return Color.BLACK
        }
        val reflectRay = Ray(comps.overPoint, comps.reflectV)
        val color = colorAt(reflectRay, remaining - 1)
        return color * comps.`object`.material.reflective
    }

    companion object {
        fun defaultWorld(): World = World().apply {
            val position = point(-10, 10, -10)
            val intensity = Color.WHITE
            light = PointLight(position, intensity)

            val s1 = Sphere()
            s1.material.color = Color(0.8, 1.0, 0.6)
            s1.material.diffuse = 0.7
            s1.material.specular = 0.2
            objects.add(s1)

            val s2 = Sphere()
            s2.transform = scaling(0.5, 0.5, 0.5)
            objects.add(s2)
        }
    }
}