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
    val w = World()

    val floor = getFloor().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = scaling(2, 1, 1)
        }
    }

    val sky = getSky().apply {
        material.pattern = RingPattern(material.color, material.color * 1.2).apply {
            transform = scaling(50.0, 1.0, 50.0)
        }
    }

    val middle = getMiddle().apply {
        material.pattern = GradientPattern(Color(1.0, 0.0, 0.1), Color.fromString("FFA500")).apply {
            transform = rotationZ(Math.PI / 2.0) * translation(1, 0, 0) * scaling(0.334, 1.0, 1.0)
        }
    }

    val right = getRight().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = rotationZ(Math.PI / 4.0) * scaling(0.02, 1.0, 1.0)
        }
    }

    val left = getLeft().apply {
        material.pattern = CheckersPattern(material.color, material.color * 0.8).apply {
            transform = rotationZ(-Math.PI / 9.0) * scaling(0.25)
        }
    }

    w.objects.addAll(listOf(floor, middle, right, left, sky))
    w.light = PointLight(point(-10, 10, -10), Color.WHITE)
    return w
}

fun render10(fileName: String, w: World) {
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

internal fun chapter10() {
    render10(prefixFileName("chapter10.png"), exampleWorldWithAllPatterns())
}

fun main() {
    chapter10()
}
