package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.ui.PNG
import kotlin.system.measureTimeMillis

fun exampleWorld9(): World {
    val w = World()

    val floor = getFloor()
    val sky = getSky()
    val middle = getMiddle()
    val right = getRight()
    val left = getLeft()

    w.objects.addAll(listOf(floor, middle, right, left, sky))
    w.light = PointLight(point(-10, 10, -10), Color.WHITE)
    return w
}

fun getFloor(): Plane = Plane().apply {
    material = Material().apply {
        color = Color(1.0, 0.9, 0.9)
        specular = 0.0
    }
}

fun getSky(): Plane = Plane().apply {
    transform = translation(0, 20, 0) * scaling(1, -1, 1)
    material = Material().apply {
        color = Color.fromString("87CEEB")
        diffuse = 0.9
        specular = 0.0
        shininess = 0.0
        ambient = 0.8
    }
}

private fun render(fileName: String) {
    val w = exampleWorld9()
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
    chapter9()
}

internal fun chapter9() {
    render(prefixFileName("chapter9.png"))
}
