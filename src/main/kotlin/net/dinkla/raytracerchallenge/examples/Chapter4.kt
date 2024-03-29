package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import java.io.File
import javax.imageio.ImageIO

private fun clock(fileName: String) {
    val canvas = Canvas(1080, 1080)

    canvas.loop { _: Int, _: Int ->
        Color.WHITE
    }

    val m = rotationZ(2*Math.PI / 12)
    var p = point(0.0, 1.0, 0.0)

    val t = translation(1.01, 1.01, 0.0)

    val colors = listOf(Color.RED, Color.GREEN, Color.BLUE)

    val xMax = 2.02
    val yMax = 2.02

    var idx = 0
    for (ignored in 0..11) {
        val q = t * p
        println(q)

        val xC = (q.x / xMax) * canvas.width
        val yC = (1.0 - (q.y / yMax)) * canvas.height

        canvas.block(xC.toInt(), yC.toInt(), 3, 3, colors[idx % 3])

        p = m * p
        idx ++
    }

    ImageIO.write(PNG.create(canvas), "png", File(fileName))
}

fun main() {
    clock(prefixFileName("clock.png"))
}
