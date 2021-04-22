package net.dinkla.raytracerchallenge.patterns

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.objects.Shape

abstract class Pattern {
    var transform: Matrix = Matrix.identity4

    abstract fun atInPatternSpace(point: Point): Color

    fun at(point: Point): Color = atInPatternSpace(point)

    fun at(shape: Shape, point: Point): Color {
        val objectSpacePoint = shape.transform.inverse() * point
        val patternSpacePoint = transform.inverse() * objectSpacePoint
        return atInPatternSpace(patternSpacePoint)
    }

}
