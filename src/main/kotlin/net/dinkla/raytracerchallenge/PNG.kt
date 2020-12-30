package net.dinkla.raytracerchallenge

import java.awt.image.BufferedImage

object PNG {
    fun create(canvas: Canvas): BufferedImage {
        val img = BufferedImage(canvas.width, canvas.height, BufferedImage.TYPE_INT_RGB)

        for (y in 0 until canvas.height) {
            for (x in 0 until canvas.width) {
                img.setRGB(x, y, canvas[x, y].toSingleInt())
            }
        }
        return img
    }
}