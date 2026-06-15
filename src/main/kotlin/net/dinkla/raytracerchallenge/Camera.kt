package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.point
import kotlin.math.tan

data class Camera(val hSize: Int, val vSize: Int, val fieldOfView: Double) {

    var pixelSize: Double

    var transform: Matrix = Matrix.identity4
        set(t) {
            field = t
            inverse = t.inverse()
        }

    private var inverse: Matrix = Matrix.identity4

    private var halfWidth: Double
    private var halfHeight: Double

    init {
        val halfView = tan(fieldOfView / 2.0)
        val aspect = hSize.toDouble() / vSize.toDouble()
        halfWidth = halfView
        halfHeight = halfView
        if (aspect >= 1.0) {
            halfHeight = halfView / aspect
        } else {
            halfWidth = halfView * aspect
        }
        pixelSize = (halfWidth * 2.0) / hSize
    }

    fun rayForPixel(x: Int, y: Int): Ray = rayForPixel(x, y, 0.5, 0.5)

    // dx/dy are the sub-pixel sample position in [0, 1); (0.5, 0.5) is the pixel centre. Supersampling
    // averages several offsets per pixel to anti-alias edges.
    fun rayForPixel(x: Int, y: Int, dx: Double, dy: Double): Ray {
        assert(0 <= x)
        assert(0 <= y)
        assert(x < hSize)
        assert(y < vSize)
        val xOffset = (x.toDouble() + dx) * pixelSize
        val yOffset = (y.toDouble() + dy) * pixelSize
        val worldX = halfWidth - xOffset
        val worldY = halfHeight - yOffset
        val pixel = inverse * point(worldX, worldY, -1.0)
        val origin = inverse * Point.ORIGIN
        val direction = (pixel - origin).normalize()
        return Ray(origin, direction)
    }

}