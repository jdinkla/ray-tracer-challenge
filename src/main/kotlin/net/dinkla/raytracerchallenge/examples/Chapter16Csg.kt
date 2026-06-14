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
import net.dinkla.raytracerchallenge.objects.Csg
import net.dinkla.raytracerchallenge.objects.Cube
import net.dinkla.raytracerchallenge.objects.Cylinder
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

private fun applyMaterial(shape: Shape, material: Material) {
    when (shape) {
        is Group -> shape.children.forEach { applyMaterial(it, material) }
        is Csg -> {
            applyMaterial(shape.left, material)
            applyMaterial(shape.right, material)
        }
        else -> shape.material = material
    }
}

private fun drill(radius: Double, orient: net.dinkla.raytracerchallenge.math.Matrix): Cylinder = Cylinder().apply {
    transform = orient * scaling(radius, 1.0, radius)
}

// (sphere ∩ cube) − (three perpendicular cylinders): a rounded cube drilled through on all 3 axes.
private fun drilledCube(): Csg {
    val roundedCube = Csg("intersection", Sphere().apply { transform = scaling(1.5, 1.5, 1.5) }, Cube())
    val holes = Csg(
        "union",
        Csg("union", drill(0.5, scaling(1.0, 1.0, 1.0)), drill(0.5, rotationZ(Math.PI / 2.0))),
        drill(0.5, rotationX(Math.PI / 2.0)),
    )
    return Csg("difference", roundedCube, holes)
}

private fun csgWorld(): World {
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

    val solid = drilledCube().apply {
        transform = translation(0.0, 1.2, 0.0) * rotationY(0.6) * rotationX(0.35) * scaling(1.1, 1.1, 1.1)
    }
    applyMaterial(
        solid,
        Material().apply {
            color = Color(0.75, 0.3, 0.15)
            diffuse = 0.7
            specular = 0.9
            shininess = 200.0
            reflective = 0.25
        },
    )

    w.objects.addAll(listOf(floor, backWall, solid))
    w.light = PointLight(point(-4.9, 6.0, -4.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(0.0, 2.8, -6.0), point(0.0, 1.1, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = csgWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun chapter16csg(width: Int = 800, height: Int = 600) {
    render(prefixFileName("chapter16csg.png"), width, height)
}

fun main() {
    chapter16csg()
}
