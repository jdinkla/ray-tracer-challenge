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

// Loads an icosahedron mesh from an OBJ file on the classpath and renders it as a glass gem.
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

private fun objWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.2), Color(0.7))
            specular = 0.0
            reflective = 0.2
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 9) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val gemMaterial = Material().apply {
        color = Color(0.1, 0.1, 0.12)
        ambient = 0.0
        diffuse = 0.1
        specular = 0.9
        shininess = 300.0
        reflective = 0.6
        transparency = 1.0
        refractiveIndex = 1.5
    }

    val gem = loadObj("/icosahedron.obj").apply {
        transform = translation(0.0, 1.6, 0.0) * rotationY(0.4) * rotationX(0.3) * scaling(0.85, 0.85, 0.85)
    }
    applyMaterial(gem, gemMaterial)

    w.objects.addAll(listOf(floor, backWall, gem))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.5, -6.0), point(0.0, 1.4, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = objWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter15objfile(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter15objfile.png"), width, height)
}

fun main() {
    chapter15objfile()
}
