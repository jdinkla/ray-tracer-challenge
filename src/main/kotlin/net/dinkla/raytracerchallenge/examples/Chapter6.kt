package net.dinkla.raytracerchallenge.examples.chapter6

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.ui.PNG

fun sphere(fileName: String) {
    val s = Sphere()
    s.material.color = Color(1.0, 0.2, 1.0)
    rayTrace(s, fileName)
}

private fun rayTrace(shape: Sphere, fileName: String) {
    val rayOrigin = point(0, 0, -5)
    val wallZ = 10.0
    val wallSize = 7.0
    val canvasPixels = 1000
    val pixelSize = wallSize / canvasPixels
    val half = wallSize / 2.0
    val canvas = Canvas(canvasPixels, canvasPixels)

    val lightPosition = point(-10, 10, -10)
    val light = PointLight(lightPosition, Color(1, 1, 1))

    canvas.loop { x: Int, y: Int ->
        val worldY = half - pixelSize * y
        val worldX = -half + pixelSize * x
        val position = point(worldX, worldY, wallZ)
        val ray = Ray(rayOrigin, (position - rayOrigin).normalize())
        val xs = shape.intersect(ray)
        val hit = xs.hit()
        if (hit != null) {
            val point = ray.position(hit.t)
            val normal = hit.`object`.normal(point)
            val eye = -ray.direction
            val material = hit.`object`.material
            lighting(material, light, point, eye, normal)
        } else {
            Color.BLACK
        }
    }
    PNG.save(canvas, fileName)
}

fun main(args: Array<String>) {
    sphere(prefixFileName("sphere.png"))
}
