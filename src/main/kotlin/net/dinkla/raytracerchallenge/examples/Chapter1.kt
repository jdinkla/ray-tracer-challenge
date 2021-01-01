package net.dinkla.raytracerchallenge.examples.chapter1

import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector

data class Projectile(val position: Point, val velocity: Vector) {
    fun tick(env: Environment, speed: Double): Projectile {
        val position = position + velocity * speed
        val velocity = velocity + (env.gravity + env.wind) * speed
        return Projectile(position, velocity)
    }
}

data class Environment(val gravity: Vector, val wind: Vector)

fun projectileExample() {
    var p = Projectile(point(0, 1, 0), vector(1, 1, 0).normalize())
    val e = Environment(vector(0.0, -0.1, 0.0), vector(-0.01, 0.0, 0.0))

    while (p.position.y >= 0) {
        println(p.position)
        p = p.tick(e, 1.0)
    }
}

fun main() {
    projectileExample()
}
