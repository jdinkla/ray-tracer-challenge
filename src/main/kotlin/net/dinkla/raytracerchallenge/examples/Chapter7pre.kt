package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.World.Companion.defaultWorld
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform

private fun firstRender(fileName: String) {
    val w = defaultWorld()
    val c = Camera(3840, 2160, 1.57079632679 / 3)
    val from = point(-2, 0, -4)
    val to = point(0, 0, 0)
    c.transform = viewTransform(from, to)
    val canvas = w.render(c)
    PNG.save(canvas, fileName)
}

fun main() {
    chapter7pre()
}

internal fun chapter7pre() {
    firstRender(prefixFileName("first.png"))
}


