package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Triangle
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// A square pyramid built from triangles, grouped so it can be rotated as a unit.
private fun face(a: Point, b: Point, c: Point, col: Color): Triangle = Triangle(a, b, c).apply {
    material = Material().apply {
        color = col
        diffuse = 0.8
        specular = 0.3
        reflective = 0.05
    }
}

private fun pyramid(): Group {
    val apex = point(0.0, 2.2, 0.0)
    val a = point(-1.3, 0.0, -1.3)
    val b = point(1.3, 0.0, -1.3)
    val c = point(1.3, 0.0, 1.3)
    val d = point(-1.3, 0.0, 1.3)
    val grey = Color(0.4, 0.4, 0.4)
    return Group().apply {
        addChild(face(apex, a, b, Color(0.85, 0.15, 0.15)))
        addChild(face(apex, b, c, Color(0.15, 0.7, 0.2)))
        addChild(face(apex, c, d, Color(0.15, 0.3, 0.85)))
        addChild(face(apex, d, a, Color(0.9, 0.75, 0.1)))
        addChild(face(a, c, b, grey))
        addChild(face(a, d, c, grey))
    }
}

private fun trianglesWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.7))
            specular = 0.0
            reflective = 0.2
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 9) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val tetra = pyramid().apply {
        transform = rotationY(0.4)
    }

    w.objects.addAll(listOf(floor, backWall, tetra))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(-3.5, 3.0, -5.5), point(0.0, 1.0, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = trianglesWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter15triangles(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter15triangles.png"), width, height)
}

fun main() {
    chapter15triangles()
}
