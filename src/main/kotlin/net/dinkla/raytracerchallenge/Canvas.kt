package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK

class Canvas(val width: Int, val height: Int) {

    private val array = Array(width * height) {
        BLACK
    }

    operator fun get(x: Int, y: Int): Color = array[y * width + x]

    operator fun set(x: Int, y: Int, c: Color) {
        array[y * width + x] = c
    }

}