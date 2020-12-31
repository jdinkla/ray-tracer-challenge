package net.dinkla.raytracerchallenge

class Intersections(private val intersections: List<Intersection>) {

    constructor(): this(listOf())

    fun count(): Int = intersections.size

    operator fun get(i: Int): Intersection = intersections[i]

}