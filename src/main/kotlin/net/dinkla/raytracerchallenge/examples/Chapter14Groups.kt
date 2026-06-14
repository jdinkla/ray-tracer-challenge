package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Cylinder
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// The book's chapter-14 hexagon: a group of six sides, each a group of a corner sphere and an edge
// cylinder. Three levels of nested transforms, which only render correctly with parent-aware
// world<->object space conversion.
private fun gold(): Material = Material().apply {
    color = Color.fromString("D4AF37")
    diffuse = 0.6
    specular = 0.9
    shininess = 200.0
    reflective = 0.3
}

private fun hexagonCorner(): Sphere = Sphere().apply {
    transform = translation(0.0, 0.0, -1.0) * scaling(0.25, 0.25, 0.25)
    material = gold()
}

private fun hexagonEdge(): Cylinder = Cylinder().apply {
    minimum = 0.0
    maximum = 1.0
    transform = translation(0.0, 0.0, -1.0) *
        rotationY(-Math.PI / 6.0) *
        rotationZ(-Math.PI / 2.0) *
        scaling(0.25, 1.0, 0.25)
    material = gold()
}

private fun hexagonSide(): Group = Group().apply {
    addChild(hexagonCorner())
    addChild(hexagonEdge())
}

private fun hexagon(): Group = Group().apply {
    for (n in 0 until 6) {
        val side = hexagonSide()
        side.transform = rotationY(n * Math.PI / 3.0)
        addChild(side)
    }
}

private fun groupsWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.7))
            specular = 0.0
            reflective = 0.2
        }
    }

    val hex = hexagon().apply {
        transform = translation(0.0, 1.0, 0.0) * rotationX(-0.5)
    }

    w.objects.addAll(listOf(floor, hex))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.5, -5.0), point(0.0, 1.0, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = groupsWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter14groups(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter14groups.png"), width, height)
}

fun main() {
    chapter14groups()
}
