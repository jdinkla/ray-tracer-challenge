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
import kotlin.math.sqrt

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
        val material = comps.`object`.material
        val surface = lighting(light, comps, isShadowed(comps.overPoint))
        val reflected = reflectedColor(comps, remaining)
        val refracted = refractedColor(comps, remaining)
        return if (material.reflective > 0.0 && material.transparency > 0.0) {
            val reflectance = schlick(comps)
            surface + reflected * reflectance + refracted * (1.0 - reflectance)
        } else {
            surface + reflected + refracted
        }
    }

    fun colorAt(ray: Ray, remaining: Int = MAX_RECURSION): Color {
        val xs = intersect(ray)
        val hit = xs.hit()
        return if (hit == null) {
            Color.BLACK
        } else {
            val comps = Computations.prepare(hit, ray, xs)
            shadeHit(comps, remaining)
        }
    }

    // samples is the number of sub-pixel samples per axis: 1 shoots a single ray through the pixel
    // centre (no anti-aliasing); n > 1 averages an n x n stratified grid of rays to anti-alias edges.
    fun render(camera: Camera, samples: Int = 1): Canvas =
        if (camera.hSize % gridSize == 0 && camera.vSize % gridSize == 0) {
            renderInParallel(camera, samples)
        } else {
            renderSequential(camera, samples)
        }

    fun renderSequential(camera: Camera, samples: Int = 1): Canvas = Canvas(camera.hSize, camera.vSize).apply {
        loop { x: Int, y: Int ->
            colorForPixel(camera, x, y, samples)
        }
    }

    fun renderInParallel(camera: Camera, samples: Int = 1): Canvas = Canvas(camera.hSize, camera.vSize).apply {
        runBlocking(Dispatchers.Default) {
            for (y in 0 until height step gridSize) {
                for (x in 0 until width step gridSize) {
                    launch {
                        for (sy in 0 until gridSize) {
                            for (sx in 0 until gridSize) {
                                set(x + sx, y + sy, colorForPixel(camera, x + sx, y + sy, samples))
                            }
                        }
                    }
                }
            }
        }
    }

    // Averages an n x n grid of sub-pixel samples; the single-sample path is unchanged (pixel centre).
    private fun colorForPixel(camera: Camera, x: Int, y: Int, samples: Int): Color {
        if (samples <= 1) {
            return colorAt(camera.rayForPixel(x, y))
        }
        val step = 1.0 / samples
        var sum = Color.BLACK
        for (sy in 0 until samples) {
            for (sx in 0 until samples) {
                sum += colorAt(camera.rayForPixel(x, y, (sx + 0.5) * step, (sy + 0.5) * step))
            }
        }
        return sum * (1.0 / (samples * samples))
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

    fun refractedColor(comps: Computations, remaining: Int = MAX_RECURSION): Color {
        if (remaining <= 0 || comps.`object`.material.transparency < Approx.EPSILON) {
            return Color.BLACK
        }
        // Snell's law: find the refracted ray direction, or detect total internal reflection.
        val nRatio = comps.n1 / comps.n2
        val cosI = comps.eyeV dot comps.normalV
        val sin2t = nRatio * nRatio * (1.0 - cosI * cosI)
        if (sin2t > 1.0) {
            return Color.BLACK
        }
        val cosT = sqrt(1.0 - sin2t)
        val direction = comps.normalV * (nRatio * cosI - cosT) - comps.eyeV * nRatio
        val refractRay = Ray(comps.underPoint, direction)
        return colorAt(refractRay, remaining - 1) * comps.`object`.material.transparency
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