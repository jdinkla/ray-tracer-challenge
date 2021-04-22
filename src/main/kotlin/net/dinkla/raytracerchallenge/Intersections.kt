package net.dinkla.raytracerchallenge

class Intersections(vararg val intersections: Intersection) {

    fun count(): Int = intersections.size

    operator fun get(i: Int): Intersection = intersections[i]

    fun hit(): Intersection? = intersections.filter { it.t > 0.0 }.minByOrNull { it.t }

    companion object {

        fun combine(intersections: List<Intersections>): Intersections {
            val all = intersections.flatMap { it.intersections.asList() }
            val sorted = all.sortedBy { it.t }
            return Intersections(*sorted.toTypedArray())
        }

    }
}