package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.examples.clock
import net.dinkla.raytracerchallenge.examples.ellipsis
import net.dinkla.raytracerchallenge.examples.projectiles
import net.dinkla.raytracerchallenge.examples.sphere

fun main(args: Array<String>) {
    val action = if (args.size == 1) args[0] else ""
    when (action) {
        "P" -> projectiles("projectile_ppm_example")
        "C" -> clock("clock.png")
        "S" -> {
            sphere("sphere.png")
            ellipsis("ellipsis.png")
        }
        else -> println("Missing argument")
    }
}
