package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.examples.*

fun main(args: Array<String>) {
    val action = if (args.size == 1) args[0] else ""
    when (action) {
        "PROJECTILE" -> projectiles(prefix("projectile_ppm_example"))
        "CLOCK" -> clock(prefix("clock.png"))
        "FLAT" -> {
            flatSphere(prefix("sphere.png"))
            flatEllipsis(prefix("ellipsis.png"))
        }
        "SPHERE" -> {
            sphere(prefix("sphere.png"))
        }
        "SPHERES" -> {
            spheres()
        }
        "FIRST" -> firstRender(prefix("first.png"))
        else -> println("Missing argument")
    }
}

private fun prefix(fileName: String) = "../" + addTimeStamp(fileName)
