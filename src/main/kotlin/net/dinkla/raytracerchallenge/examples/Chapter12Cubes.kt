package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Cube
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// Three cubes on a checkered floor: a matte coloured box, a mirror box, and a glass box, to show
// the new primitive together with reflection and refraction.
private fun cubesWorld(): World {
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

    // Matte red box, slightly rotated.
    val redBox = Cube().apply {
        transform = translation(-2.8, 1.0, 2.0) * rotationY(0.4)
        material = Material().apply {
            color = Color(0.8, 0.1, 0.1)
            diffuse = 0.7
            specular = 0.2
            reflective = 0.05
        }
    }

    // Mirror box, a tall slab.
    val mirrorBox = Cube().apply {
        transform = translation(2.9, 1.5, 2.5) * rotationY(-0.35) * scaling(0.9, 1.5, 0.9)
        material = Material().apply {
            color = Color(0.1, 0.1, 0.12)
            diffuse = 0.1
            specular = 1.0
            shininess = 300.0
            reflective = 0.85
        }
    }

    // Glass box, centre stage.
    val glassBox = Cube().apply {
        transform = translation(0.0, 1.0, 1.0) * rotationY(0.5)
        material = Material().apply {
            color = Color(0.05, 0.08, 0.06)
            ambient = 0.0
            diffuse = 0.05
            specular = 0.9
            shininess = 300.0
            reflective = 0.8
            transparency = 1.0
            refractiveIndex = 1.5
        }
    }

    w.objects.addAll(listOf(floor, backWall, redBox, mirrorBox, glassBox))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 3.0, -7.0), point(0.0, 1.0, 1.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = cubesWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter12cubes(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter12cubes.png"), width, height)
}

fun main() {
    chapter12cubes()
}
