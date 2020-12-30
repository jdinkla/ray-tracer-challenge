package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

data class Projectile(val position: Point, val velocity: Vector) {
    fun tick(env: Environment, speed: Double): Projectile {
        val position = position + velocity * speed
        val velocity = velocity + (env.gravity + env.wind) * speed
        return Projectile(position, velocity)
    }
}
data class Environment(val gravity: Vector, val wind: Vector)

fun projectile_example() {
    var p = Projectile(point(0, 1, 0), vector(1, 1, 0).normalize())
    val e = Environment(vector(0.0, -0.1, 0.0), vector(-0.01, 0.0, 0.0))

    while (p.position.y >= 0) {
        println(p.position)
        p = p.tick(e, 1.0)
    }
}

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

fun save_as_ppm(canvas: Canvas) {
    val ppm = PPM.create(canvas)
    File("projectile_ppm_example.ppm").writeText(ppm.contents)
}

fun save_as_png(canvas: Canvas) {
    val file = File("projectile_ppm_example.png")
    val img = PNG.create(canvas)
    ImageIO.write(img,"png", file)
}

fun main() {
    val canvas = simulate()
    save_as_ppm(canvas)
    save_as_png(canvas)
}
