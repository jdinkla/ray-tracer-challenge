package net.dinkla.raytracerchallenge.examples.chapter2

import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.examples.chapter1.Environment
import net.dinkla.raytracerchallenge.examples.chapter1.Projectile
import net.dinkla.raytracerchallenge.math.*
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.ui.PPM
import java.io.File
import javax.imageio.ImageIO

private fun max(p: Projectile, e: Environment, speed: Double): Pair<Double, Double> {
    var p1 = p
    var xMax = 0.0
    var yMax = 0.0
    while (p1.position.y >= 0) {
        if (p1.position.x > xMax) {
            xMax = p1.position.x
        }
        if (p1.position.y > yMax) {
            yMax = p1.position.y
        }
        p1 = p1.tick(e, speed)
    }
    yMax *= 1.05
    return Pair(xMax, yMax)
}

private fun simulate(): Canvas {
    val speed = 0.01
    val canvas = Canvas(1920, 1080)
    var p = Projectile(point(0, 1, 0), vector(1, 1, 0).normalize())
    val e = Environment(vector(0.0, -0.1, 0.0), vector(-0.01, 0.0, 0.0))
    val (xMax, yMax) = max(p, e, speed)

    while (p.position.y >= 0) {
        println(p.position)
        val xC = (p.position.x / xMax) * canvas.width
        val yC = (1.0 - (p.position.y / yMax)) * canvas.height
        canvas[xC.toInt(), yC.toInt()] = Color.WHITE
        p = p.tick(e, speed)
    }
    return canvas
}

fun saveAsPpm(canvas: Canvas, fileName: String) {
    val ppm = PPM.create(canvas)
    File(fileName).writeText(ppm.contents)
}

fun saveAsPng(canvas: Canvas, fileName: String) {
    val file = File(fileName)
    val img = PNG.create(canvas)
    ImageIO.write(img,"png", file)
}

fun main() {
    val fileName = "projectile_ppm_example"
    val canvas = simulate()
    saveAsPpm(canvas, prefixFileName("$fileName.ppm"))
    saveAsPng(canvas, prefixFileName("$fileName.png"))
}
