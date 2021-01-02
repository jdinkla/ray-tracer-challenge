package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point

data class Material(
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0,
) {

    var color: Color = Color.WHITE
    var pattern: Pattern? = null

    fun color(point: Point): Color = if (pattern == null) color else pattern!!.at(point)

}