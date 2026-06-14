package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Cylinder
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// Three capped pillars on a checkered floor: matte, mirror, and glass, to show truncated and
// closed cylinders together with reflection and refraction.
private fun cylindersWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.7))
            specular = 0.0
            reflective = 0.15
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 9) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val redPillar = Cylinder().apply {
        minimum = 0.0
        maximum = 2.6
        closed = true
        transform = translation(-2.7, 0.0, 1.5) * scaling(0.6, 1.0, 0.6)
        material = Material().apply {
            color = Color(0.8, 0.1, 0.1)
            diffuse = 0.7
            specular = 0.3
            reflective = 0.05
        }
    }

    val mirrorPillar = Cylinder().apply {
        minimum = 0.0
        maximum = 3.2
        closed = true
        transform = translation(2.7, 0.0, 2.0) * scaling(0.55, 1.0, 0.55)
        material = Material().apply {
            color = Color(0.1, 0.1, 0.12)
            diffuse = 0.1
            specular = 1.0
            shininess = 300.0
            reflective = 0.85
        }
    }

    val glassPillar = Cylinder().apply {
        minimum = 0.0
        maximum = 2.0
        closed = true
        transform = translation(0.0, 0.0, 0.0) * scaling(0.8, 1.0, 0.8)
        material = Material().apply {
            color = Color(0.05, 0.08, 0.06)
            ambient = 0.0
            diffuse = 0.05
            specular = 0.9
            shininess = 300.0
            reflective = 0.7
            transparency = 1.0
            refractiveIndex = 1.5
        }
    }

    w.objects.addAll(listOf(floor, backWall, redPillar, mirrorPillar, glassPillar))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 3.0, -7.0), point(0.0, 1.2, 1.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = cylindersWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter13cylinders(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter13cylinders.png"), width, height)
}

fun main() {
    chapter13cylinders()
}
