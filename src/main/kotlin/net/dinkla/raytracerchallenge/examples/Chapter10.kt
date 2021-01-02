package net.dinkla.raytracerchallenge.examples.chapter10

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.examples.chapter7.getLeft
import net.dinkla.raytracerchallenge.examples.chapter7.getMiddle
import net.dinkla.raytracerchallenge.examples.chapter7.getRight
import net.dinkla.raytracerchallenge.examples.chapter9.getFloor
import net.dinkla.raytracerchallenge.examples.chapter9.getSky
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.ui.PNG
import kotlin.system.measureTimeMillis

private fun exampleWorldWithStripedSpheres(): World {
    val w = World()

    val floor = getFloor().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = scaling(2, 1, 1)
        }
    }

    val sky = getSky().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = scaling(2.0, 1.0, 1.0)
        }
    }

    val middle = getMiddle().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = rotationZ(Math.PI / 8.0) * scaling(0.1, 1.0, 1.0)
        }
    }

    val right = getRight().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = rotationZ(Math.PI / 4.0) * scaling(0.02, 1.0, 1.0)
        }
    }

    val left = getLeft().apply {
        material.pattern = StripePattern(material.color, material.color * 0.8).apply {
            transform = rotationZ(-Math.PI / 9.0) * scaling(0.1, 1.0, 1.0)
        }
    }

    w.objects.addAll(listOf(floor, middle, right, left, sky))
    w.light = PointLight(point(-10, 10, -10), Color.WHITE)
    return w
}

fun render(fileName: String, w: World) {
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
    render(prefixFileName("chapter10_stripes.png"), exampleWorldWithStripedSpheres())
}


