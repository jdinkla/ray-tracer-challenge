package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.SmoothTriangle
import net.dinkla.raytracerchallenge.objects.Triangle
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.math.cos
import kotlin.math.sin
import kotlin.system.measureTimeMillis

// Builds a coarse UV-sphere mesh. With flat triangles the facets are obvious; with smooth triangles
// the radial vertex normals interpolate so the same mesh shades as a round sphere.
private fun sphereMesh(smooth: Boolean, lat: Int, lon: Int, col: Color): Group {
    fun vertex(i: Int, j: Int): Point {
        val theta = Math.PI * i / lat
        val phi = 2.0 * Math.PI * j / lon
        return point(sin(theta) * cos(phi), cos(theta), sin(theta) * sin(phi))
    }

    fun material() = Material().apply {
        color = col
        diffuse = 0.7
        specular = 0.9
        shininess = 200.0
    }

    fun tri(a: Point, b: Point, c: Point): Triangle {
        val t = if (smooth) {
            SmoothTriangle(a, b, c, vector(a.x, a.y, a.z), vector(b.x, b.y, b.z), vector(c.x, c.y, c.z))
        } else {
            Triangle(a, b, c)
        }
        t.material = material()
        return t
    }

    val g = Group()
    for (i in 0 until lat) {
        for (j in 0 until lon) {
            val a = vertex(i, j)
            val b = vertex(i + 1, j)
            val c = vertex(i + 1, j + 1)
            val d = vertex(i, j + 1)
            g.addChild(tri(a, b, c))
            g.addChild(tri(a, c, d))
        }
    }
    return g
}

private fun smoothTrianglesWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.7))
            specular = 0.0
            reflective = 0.15
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 8) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val flat = sphereMesh(smooth = false, lat = 8, lon = 12, col = Color(0.9, 0.3, 0.2)).apply {
        transform = translation(-1.6, 1.3, 0.0) * scaling(1.2, 1.2, 1.2)
    }
    val smooth = sphereMesh(smooth = true, lat = 8, lon = 12, col = Color(0.2, 0.5, 0.9)).apply {
        transform = translation(1.6, 1.3, 0.0) * scaling(1.2, 1.2, 1.2)
    }

    w.objects.addAll(listOf(floor, backWall, flat, smooth))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.0, -6.0), point(0.0, 1.2, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = smoothTrianglesWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter15smoothtriangles(width: Int = 700, height: Int = 500) {
    render(prefixFileName("chapter15smoothtriangles.png"), width, height)
}

fun main() {
    chapter15smoothtriangles()
}
