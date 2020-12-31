package net.dinkla.raytracerchallenge

class Intersections(private vararg val intersections: Intersection) {

    fun count(): Int = intersections.size

    operator fun get(i: Int): Intersection = intersections[i]

    fun hit(): Intersection? {
        return intersections.filter { it.t > 0.0 }.minByOrNull { it.t }
    }

}