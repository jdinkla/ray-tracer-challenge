package net.dinkla.raytracerchallenge.examples

import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.parser.parseObjFile
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// The book's chapter-15 "putting it together": the red Utah teapot in a checkered room.
// teapot-low.obj carries vertex normals, so the parser builds SmoothTriangles and the surface
// shades smoothly despite the low face count.

private fun loadObj(resource: String): Group {
    val content = requireNotNull(object {}.javaClass.getResource(resource)).readText()
    return parseObjFile(content).objToGroup()
}

private fun applyMaterial(shape: Shape, material: Material) {
    if (shape is Group) {
        shape.children.forEach { applyMaterial(it, material) }
    } else {
        shape.material = material
    }
}

private fun teapotWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.35), Color(0.65))
            specular = 0.0
            reflective = 0.1
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 6) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val leftWall = Plane().apply {
        transform = translation(-6, 0, 0) * rotationY(Math.PI / 2.0) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val redMatte = Material().apply {
        color = Color(0.75, 0.12, 0.12)
        ambient = 0.1
        diffuse = 0.8
        specular = 0.3
        shininess = 80.0
        reflective = 0.05
    }

    // The mesh is modelled Z-up at ~16-unit scale, so stand it upright (rotX -90°), turn it for a
    // three-quarter view, scale down and drop it onto the floor.
    val teapot = loadObj("/teapot-low.obj").apply {
        transform = scaling(0.1, 0.1, 0.1) * rotationY(-0.6) * rotationX(-Math.PI / 2.0)
    }
    applyMaterial(teapot, redMatte)

    w.objects.addAll(listOf(floor, backWall, leftWall, teapot))
    w.light = PointLight(point(-4.0, 7.0, -5.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.2, -5.0), point(0.0, 0.8, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = teapotWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter15teapot(width: Int = 600, height: Int = 400) {
    render(prefixFileName("chapter15teapot.png"), width, height)
}

fun main() {
    chapter15teapot()
}
