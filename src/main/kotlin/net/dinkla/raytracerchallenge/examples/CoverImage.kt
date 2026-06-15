package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Cube
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// The book's cover image (appendix scene). Faithfully transcribed from the canonical cover.yaml:
// a cluster of pastel cubes around a glassy reflective sphere on a white backdrop.
//
// Each object is built by a "<size>-object" transform -- nudge by (1,-1,1) then scale s -- and then
// translated into place by a per-object offset. The per-object offset positions the (already scaled)
// object in world space, so it sits outermost: translation(pos) * scaling(s) * translation(1,-1,1).
//
// Note: the YAML defines a second, dim fill light; World holds a single PointLight, so only the
// primary light is used here.

private const val LARGE = 3.5
private const val MEDIUM = 3.0
private const val SMALL = 2.0

private val WHITE = std(Color(1.0, 1.0, 1.0))
private val BLUE = std(Color(0.537, 0.831, 0.914))
private val RED = std(Color(0.941, 0.322, 0.388))
private val PURPLE = std(Color(0.373, 0.404, 0.550))

// The shared cube material: matte with a hint of reflection.
private fun std(c: Color): Material = Material().apply {
    color = c
    diffuse = 0.7
    ambient = 0.1
    specular = 0.0
    reflective = 0.1
}

// translation(pos) * scaling(size) * translation(1,-1,1): the size-object nudge/scale, then the
// per-object offset placing it in world space.
private fun positioned(size: Double, x: Double, y: Double, z: Double): Matrix =
    translation(x, y, z) * scaling(size) * translation(1.0, -1.0, 1.0)

private data class CubeSpec(val material: Material, val size: Double, val x: Double, val y: Double, val z: Double)

private val cubes = listOf(
    CubeSpec(WHITE, MEDIUM, 4.0, 0.0, 0.0),
    CubeSpec(BLUE, LARGE, 8.5, 1.5, -0.5),
    CubeSpec(RED, LARGE, 0.0, 0.0, 4.0),
    CubeSpec(WHITE, SMALL, 4.0, 0.0, 4.0),
    CubeSpec(PURPLE, MEDIUM, 7.5, 0.5, 4.0),
    CubeSpec(WHITE, MEDIUM, -0.25, 0.25, 8.0),
    CubeSpec(BLUE, LARGE, 4.0, 1.0, 7.5),
    CubeSpec(RED, MEDIUM, 10.0, 2.0, 7.5),
    CubeSpec(WHITE, SMALL, 8.0, 2.0, 12.0),
    CubeSpec(WHITE, SMALL, 20.0, 1.0, 9.0),
    CubeSpec(BLUE, LARGE, -0.5, -5.0, 0.25),
    CubeSpec(RED, LARGE, 4.0, -4.0, 0.0),
    CubeSpec(WHITE, LARGE, 8.5, -4.0, 0.0),
    CubeSpec(WHITE, LARGE, 0.0, -4.0, 4.0),
    CubeSpec(PURPLE, LARGE, -0.5, -4.5, 8.0),
    CubeSpec(WHITE, LARGE, 0.0, -8.0, 4.0),
    CubeSpec(WHITE, LARGE, -0.5, -8.5, 8.0),
)

private fun backdrop(): Plane = Plane().apply {
    transform = translation(0, 0, 500) * rotationX(Math.PI / 2.0)
    material = Material().apply {
        color = Color(1.0, 1.0, 1.0)
        ambient = 1.0
        diffuse = 0.0
        specular = 0.0
    }
}

private fun centralSphere(): Sphere = Sphere().apply {
    transform = positioned(LARGE, 0.0, 0.0, 0.0)
    material = Material().apply {
        color = Color(0.373, 0.404, 0.550)
        diffuse = 0.2
        ambient = 0.0
        specular = 1.0
        shininess = 200.0
        reflective = 0.7
        transparency = 0.7
        refractiveIndex = 1.5
    }
}

private fun coverWorld(): World {
    val w = World()
    val shapes: MutableList<Shape> = mutableListOf(backdrop(), centralSphere())
    cubes.forEach { spec ->
        shapes.add(Cube().apply {
            transform = positioned(spec.size, spec.x, spec.y, spec.z)
            material = spec.material
        })
    }
    w.objects.addAll(shapes)
    w.light = PointLight(point(50.0, 100.0, -50.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, 0.785).apply {
        transform = viewTransform(point(-6.0, 6.0, -10.0), point(6.0, 0.0, 6.0), vector(-0.45, 1.0, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = coverWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun coverImage(width: Int = 600, height: Int = 600) {
    render(prefixFileName("cover.png"), width, height)
}

fun main() {
    coverImage()
}
