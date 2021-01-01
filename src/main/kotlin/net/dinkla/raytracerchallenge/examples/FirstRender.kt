package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.PNG
import net.dinkla.raytracerchallenge.World.Companion.defaultWorld
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.viewTransform

fun firstRender(fileName: String) {
    val w = defaultWorld()
    val c = Camera(1001, 1001, 1.57079632679/3)
    val from = point(0, 0, -2)
    val to = point(0, 0, 0)
    val up = vector(0, 1, 0)
    c.transform = viewTransform(from, to, up)
    val canvas = w.render(c)
    PNG.save(canvas, fileName)
}
