package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.patterns.Pattern

data class Material(
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0,
    var reflective: Double = 0.0
) {

    var color: Color = Color.WHITE

    var pattern: Pattern? = null

    fun color(point: Point): Color = if (pattern == null) color else pattern!!.at(point)

    fun color(shape: Shape, point: Point): Color = if (pattern == null) color else pattern!!.at(shape, point)

}