package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector

// Constructive Solid Geometry: combines two shapes by a set operation (union / intersection /
// difference). It intersects both operands, then keeps only the intersections the operation allows.
class Csg(val operation: String, val left: Shape, val right: Shape) : Shape() {

    init {
        left.parent = this
        right.parent = this
    }

    override fun includes(other: Shape): Boolean = left.includes(other) || right.includes(other)

    override fun intersectInObjectSpace(ray: Ray): Intersections {
        val combined = Intersections.combine(listOf(left.intersect(ray), right.intersect(ray)))
        return filterIntersections(combined)
    }

    fun filterIntersections(xs: Intersections): Intersections {
        var inLeft = false
        var inRight = false
        val result = mutableListOf<Intersection>()
        for (idx in 0 until xs.count()) {
            val i = xs[idx]
            val leftHit = left.includes(i.`object`)
            if (intersectionAllowed(operation, leftHit, inLeft, inRight)) {
                result.add(i)
            }
            if (leftHit) {
                inLeft = !inLeft
            } else {
                inRight = !inRight
            }
        }
        return Intersections(*result.toTypedArray())
    }

    override fun normalInObjectSpace(point: Point): Vector {
        throw UnsupportedOperationException("a CSG has no local normal; query its operands instead")
    }
}

// Whether an intersection survives the CSG operation, given whether it hit the left operand and
// whether the ray is currently inside the left / right operand.
fun intersectionAllowed(operation: String, leftHit: Boolean, inLeft: Boolean, inRight: Boolean): Boolean =
    when (operation) {
        "union" -> (leftHit && !inRight) || (!leftHit && !inLeft)
        "intersection" -> (leftHit && inRight) || (!leftHit && inLeft)
        "difference" -> (leftHit && !inRight) || (!leftHit && inLeft)
        else -> false
    }
