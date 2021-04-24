package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.patterns.GradientPattern
import net.dinkla.raytracerchallenge.patterns.RingPattern
import net.dinkla.raytracerchallenge.patterns.StripePattern
import net.dinkla.raytracerchallenge.ui.PNG
import kotlin.system.measureTimeMillis

private fun exampleWorldWithAllPatterns(): World {
    val w = exampleWorld9()

    for (o in w.objects) {
        o.material.reflective = 0.1
    }

    return w
}

private fun render(fileName: String, w: World) {
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

internal fun chapter11() {
    render(prefixFileName("chapter11.png"), exampleWorldWithAllPatterns())
}

fun main() {
    chapter11()
}
