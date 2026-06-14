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
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// A checkered room with a solid glass sphere and a hollow glass shell (air bubble) so refraction
// and total internal reflection are clearly visible.
private fun glassWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.15), Color(0.85))
            specular = 0.0
            reflective = 0.1
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 8) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    // Solid glass sphere resting on the floor.
    val glass = Sphere().apply {
        transform = translation(-0.7, 1.0, 0.0)
        material = Material().apply {
            color = Color(0.05, 0.05, 0.08)
            ambient = 0.0
            diffuse = 0.05
            specular = 0.9
            shininess = 300.0
            reflective = 0.9
            transparency = 1.0
            refractiveIndex = 1.5
        }
    }

    // Hollow glass shell: an outer glass sphere with a smaller air sphere inside it.
    val outerShell = Sphere().apply {
        transform = translation(1.6, 1.0, -0.5)
        material = glass.material.copy()
    }
    val airBubble = Sphere().apply {
        transform = translation(1.6, 1.0, -0.5) * scaling(0.6, 0.6, 0.6)
        material = glass.material.copy().apply { refractiveIndex = 1.0000034 }
    }

    // A coloured sphere behind the glass so the refraction bends something recognizable.
    val red = Sphere().apply {
        transform = translation(-0.3, 0.5, 2.5) * scaling(0.5, 0.5, 0.5)
        material = Material().apply {
            color = Color(0.9, 0.2, 0.2)
            diffuse = 0.7
            specular = 0.3
        }
    }

    w.objects.addAll(listOf(floor, backWall, glass, outerShell, airBubble, red))
    w.light = PointLight(point(-4.9, 4.9, -5.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.0, -5.0), point(0.0, 0.8, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = glassWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter11refraction(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter11refraction.png"), width, height)
}

fun main() {
    chapter11refraction()
}
