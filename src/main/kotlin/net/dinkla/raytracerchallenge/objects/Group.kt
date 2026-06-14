package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

// A container shape: applies its own transform, then defers to its children. Has no surface of its
// own, so it intersects by combining child intersections and never computes a local normal.
class Group : Shape() {

    val children: MutableList<Shape> = mutableListOf()

    fun isEmpty(): Boolean = children.isEmpty()

    fun addChild(child: Shape) {
        child.parent = this
        children.add(child)
    }

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val all = children.map { it.intersect(ray) }
        return Intersections.combine(all)
    }

    override fun normalInObjectSpace(point: Point): Vector {
        throw UnsupportedOperationException("a group has no local normal; query its children instead")
    }

    override fun includes(other: Shape): Boolean = children.any { it.includes(other) }
}
