package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK

class Canvas(val width: Int, val height: Int) {

    private val array: Array<Color> = Array(width * height) {
        BLACK
    }

    operator fun get(x: Int, y: Int): Color {
        assert(x in 0 until width)
        assert(y in 0 until height)
        return array[index(x, y)]
    }

    operator fun set(x: Int, y: Int, c: Color) {
        assert(x in 0 until width)
        assert(y in 0 until height)
        array[index(x, y)] = c
    }

    fun block(x: Int, y: Int, sx: Int, sy: Int, color: Color) {
        for (dy in -sy..sy) {
            for (dx in -sx..sy) {
                array[index(x+dx, y+dy)] = color
            }
        }
    }

    fun loop(f: (Int, Int) -> Color) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                array[index(x, y)] = f(x, y)
            }
        }
    }

    private fun index(x: Int, y: Int) = y * width + x
}