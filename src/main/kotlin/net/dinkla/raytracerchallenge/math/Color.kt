package net.dinkla.raytracerchallenge.math

import net.dinkla.raytracerchallenge.math.Approx.compare
import java.util.Objects
import kotlin.math.max
import kotlin.math.min

data class Color(val red: Double, val green: Double, val blue: Double) {

    constructor(red: Int, green: Int, blue: Int) : this(red.toDouble(), green.toDouble(), blue.toDouble())
    constructor(value: Int) : this(value.toDouble(), value.toDouble(), value.toDouble())
    constructor(value: Double) : this(value, value, value)

    operator fun plus(c: Color) = Color(red + c.red, green + c.green, blue + c.blue)
    operator fun minus(c: Color) = Color(red - c.red, green - c.green, blue - c.blue)
    operator fun times(s: Double) = Color(red * s, green * s, blue * s)
    operator fun times(c: Color) = Color(red * c.red, green * c.green, blue * c.blue)

    override fun equals(other: Any?): Boolean {
        val p: Color = other as? Color ?: return false
        return compare(red, p.red) && compare(green, p.green) && compare(blue, p.blue)
    }

    override fun hashCode(): Int = Objects.hash(red, green, blue)

    fun toInt(): Triple<Int, Int, Int> {
        val r = (clamp(red) * 255).toInt()
        val g = (clamp(green) * 255).toInt()
        val b = (clamp(blue) * 255).toInt()
        return Triple(r, g, b)
    }

    private fun clamp(d: Double) = max(0.0, min(d, 1.0))

    companion object {
        val BLACK = Color(0)
        val WHITE = Color(1.0)

    }
}
