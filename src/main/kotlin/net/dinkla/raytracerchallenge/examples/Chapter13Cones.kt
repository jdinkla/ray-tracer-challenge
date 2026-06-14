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
import net.dinkla.raytracerchallenge.objects.Cone
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// Three upward cones (apex on top, capped base on the floor) in matte, mirror, and glass.
private fun cone(height: Double, radius: Double, x: Double, z: Double): Cone = Cone().apply {
    minimum = -height
    maximum = 0.0
    closed = true
    transform = translation(x, height, z) * scaling(radius, 1.0, radius)
}

private fun conesWorld(): World {
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

    val redCone = cone(2.6, 0.8, -2.8, 1.5).apply {
        material = Material().apply {
            color = Color(0.8, 0.1, 0.1)
            diffuse = 0.7
            specular = 0.3
            reflective = 0.05
        }
    }

    val mirrorCone = cone(3.2, 0.7, 2.8, 2.0).apply {
        material = Material().apply {
            color = Color(0.1, 0.1, 0.12)
            diffuse = 0.1
            specular = 1.0
            shininess = 300.0
            reflective = 0.85
        }
    }

    val glassCone = cone(2.2, 0.9, 0.0, 0.0).apply {
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

    w.objects.addAll(listOf(floor, backWall, redCone, mirrorCone, glassCone))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 3.0, -7.0), point(0.0, 1.2, 1.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = conesWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter13cones(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter13cones.png"), width, height)
}

fun main() {
    chapter13cones()
}
