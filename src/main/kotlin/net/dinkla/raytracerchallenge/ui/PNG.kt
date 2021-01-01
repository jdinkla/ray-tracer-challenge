package net.dinkla.raytracerchallenge.ui

import net.dinkla.raytracerchallenge.Canvas
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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

    fun save(canvas: Canvas, fileName: String) {
        ImageIO.write(create(canvas), "png", File(fileName))
    }



}