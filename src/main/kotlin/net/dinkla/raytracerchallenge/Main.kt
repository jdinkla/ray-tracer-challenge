package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.examples.*

fun main(args: Array<String>) {
    val action = if (args.size == 1) args[0] else ""
    when (action) {
        "P" -> projectiles(prefix("projectile_ppm_example"))
        "C" -> clock(prefix("clock.png"))
        "F" -> {
            flatSphere(prefix("sphere.png"))
            flatEllipsis(prefix("ellipsis.png"))
        }
        "S" -> {
            sphere(prefix("sphere.png"))
        }
        "SPHERES" -> {
            spheres()
        }
        else -> println("Missing argument")
    }
}

private fun prefix(fileName: String) = "../" + addTimeStamp(fileName)
