package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.approx

data class Color(val red: Double, val green: Double, val blue: Double) {

    constructor(red: Int, green: Int, blue: Int) : this(red.toDouble(), green.toDouble(), blue.toDouble())
    constructor(value: Int) : this(value.toDouble(), value.toDouble(), value.toDouble())

    operator fun plus(c: Color) = Color(red + c.red, green + c.green, blue + c.blue)
    operator fun minus(c: Color) = Color(red - c.red, green - c.green, blue - c.blue)
    operator fun times(s: Double) = Color(red * s, green * s, blue * s)
    operator fun times(c: Color) = Color(red * c.red, green * c.green, blue * c.blue)

    override fun equals(other: Any?): Boolean {
        val p: Color = other as? Color ?: return false
        return approx(red, p.red) && approx(green, p.green) && approx(blue, p.blue)
    }

    companion object {
        val BLACK = Color(0)

    }
}
