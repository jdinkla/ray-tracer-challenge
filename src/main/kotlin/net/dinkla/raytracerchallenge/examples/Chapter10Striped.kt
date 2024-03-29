package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.patterns.StripePattern
import net.dinkla.raytracerchallenge.prefixFileName

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

internal fun chapter10striped() {
    render10(prefixFileName("chapter10_stripes.png"), exampleWorldWithStripedSpheres())
}

fun main() {
    chapter10striped()
}


