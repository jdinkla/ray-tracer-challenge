package net.dinkla.raytracerchallenge.examples.chapter5

import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.shearing
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import java.io.File
import javax.imageio.ImageIO

const val offsetInPixel = 100
val direction = vector(0, 0, 1)
const val pixel = 1080
const val scaling = 2.0/(pixel - offsetInPixel)
const val offsetInObjectModel = offsetInPixel.toDouble() / pixel
const val translate = -1.0 - offsetInObjectModel
val transform = translation(translate, translate, 0.0) * scaling(scaling, scaling, 0.0)

fun flatSphere(fileName: String) {
    val s = Sphere()
    rayTrace(s, fileName)
}

fun flatEllipsis(fileName: String) {
    val s = Sphere()
    s.transform = rotationZ(-Math.PI / 4.0) * shearing(0.15, 0.0, 0.0, 0.0, 0.0, 0.0)
    rayTrace(s, fileName)
}

private fun rayTrace(s: Sphere, fileName: String) {
    val canvas = Canvas(pixel, pixel)
    canvas.loop { x: Int, y: Int ->
        val p = transform * point(x, y, -3)
        val r = Ray(p, direction)
        val xs = s.intersect(r)
        if (xs.hit() != null) {
            Color.RED
        } else {
            Color.BLACK
        }
    }
    ImageIO.write(PNG.create(canvas), "png", File(fileName))
}

fun main(args: Array<String>) {
    flatSphere(prefixFileName("sphere.png"))
    flatEllipsis(prefixFileName("ellipsis.png"))
}

