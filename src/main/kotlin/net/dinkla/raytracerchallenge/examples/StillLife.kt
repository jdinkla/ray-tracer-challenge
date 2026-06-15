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
import net.dinkla.raytracerchallenge.objects.Cylinder
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// A small still life that puts the whole renderer on show at once: a glass sphere (refraction +
// Fresnel), a chrome sphere (reflection), a matte cube and a closed cylinder, on a reflective
// checkered floor backed by a checkered wall.

private fun glass(): Sphere = Sphere().apply {
    transform = translation(-2.2, 1.0, 0.3)
    material = Material().apply {
        color = Color(0.05, 0.05, 0.07)
        ambient = 0.0
        diffuse = 0.1
        specular = 1.0
        shininess = 300.0
        reflective = 0.9
        transparency = 1.0
        refractiveIndex = 1.5
    }
}

private fun chrome(): Sphere = Sphere().apply {
    transform = translation(2.2, 1.0, 0.6)
    material = Material().apply {
        color = Color(0.18, 0.19, 0.22)
        ambient = 0.05
        diffuse = 0.1
        specular = 1.0
        shininess = 300.0
        reflective = 0.9
    }
}

private fun redCube(): Cube = Cube().apply {
    transform = translation(0.0, 0.7, -1.1) * rotationY(0.5) * scaling(0.7, 0.7, 0.7)
    material = Material().apply {
        color = Color(0.8, 0.16, 0.16)
        diffuse = 0.7
        specular = 0.3
        shininess = 120.0
        reflective = 0.08
    }
}

private fun pillar(): Cylinder = Cylinder().apply {
    minimum = 0.0
    maximum = 1.6
    closed = true
    transform = translation(0.2, 0.0, 2.2) * scaling(0.65, 1.0, 0.65)
    material = Material().apply {
        color = Color(0.18, 0.55, 0.45)
        diffuse = 0.7
        specular = 0.4
        shininess = 150.0
        reflective = 0.15
    }
}

private fun stillLifeWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.75))
            specular = 0.0
            reflective = 0.3
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 7) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    w.objects.addAll(listOf(floor, backWall, glass(), chrome(), redCube(), pillar()))
    w.light = PointLight(point(-4.0, 7.0, -5.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.8, -8.0), point(0.0, 0.9, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = stillLifeWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun stillLife(width: Int = 700, height: Int = 450) {
    render(prefixFileName("stilllife.png"), width, height)
}

fun main() {
    stillLife()
}
