package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Matrix.Companion.matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.Vector

fun viewTransform(from: Point, to: Point, up: Vector = Vector.UP): Matrix {
    val forward = (to - from).normalize()
    val upNorm = up.normalize()
    val left = forward cross upNorm
    val trueUp = left cross forward

    val orientation = matrix(
        left.x, left.y, left.z, 0.0,
        trueUp.x, trueUp.y, trueUp.z, 0.0,
        -forward.x, -forward.y, -forward.z, 0.0,
        0.0, 0.0, 0.0, 1.0
    )

    return orientation * translation(-from)
}