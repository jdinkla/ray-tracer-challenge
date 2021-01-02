package net.dinkla.raytracerchallenge.examples.chapter9

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.ui.PNG
import kotlin.system.measureTimeMillis

private fun exampleWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            color = Color(1.0, 0.9, 0.9)
            specular = 0.0
        }
    }

    val sky = Plane().apply {
        transform = translation(0, 20, 0) * scaling(1, -1, 1)
        material = Material().apply {
            color = Color.fromString("87CEEB")
            diffuse = 0.9
            specular = 0.0
            shininess = 0.0
            ambient = 0.8
        }
    }

    val middle = Sphere().apply {
        transform = translation(-0.5, 1.0, 0.5)
        material = Material().apply {
            color = Color(0.1, 1.0, 0.5)
            diffuse = 0.7
            specular = 0.3
        }
    }

    val right = Sphere().apply {
        transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
        material = Material().apply {
            color = Color(0.5, 1.0, 0.1)
            diffuse = 0.7
            specular = 0.3
        }
    }

    val left = Sphere().apply {
        transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33)
        material = Material().apply {
            color = Color(1.0, 0.8, 0.1)
            diffuse = 0.7
            specular = 0.3
        }
    }

    w.objects.addAll(listOf(floor, middle, right, left, sky))
    w.light = PointLight(point(-10, 10, -10), Color.WHITE)
    return w
}

fun render(fileName: String) {
    val w = exampleWorld()
    val c = Camera(3840, 2160, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 1.5, -5.0), point(0, 1, 0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = w.render(c)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

fun main() {
    render(prefixFileName("chapter9.png"))
}


