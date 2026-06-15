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
import net.dinkla.raytracerchallenge.objects.Csg
import net.dinkla.raytracerchallenge.objects.Cube
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Plane
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.patterns.CheckersPattern
import net.dinkla.raytracerchallenge.prefixFileName
import net.dinkla.raytracerchallenge.ui.PNG
import net.dinkla.raytracerchallenge.viewTransform
import kotlin.system.measureTimeMillis

// A six-sided die as a CSG showcase: (cube intersect sphere) for a rounded cube, minus a sphere at
// each pip so the dots are carved-in dimples. Opposite faces sum to seven; with the default
// orientation +y=1, +z=2, +x=3 the camera sees the 1-2-3 corner.

private const val PIP = 0.5 // in-plane offset of a pip from the face centre
private const val FACE = 1.0 // distance of a face from the die centre
private const val PIP_RADIUS = 0.18

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

// Pip centres for all six faces (21 total). Each triple is a sphere centre on a face.
private val pipCentres: List<Triple<Double, Double, Double>> = listOf(
    // 1 - top (+y)
    Triple(0.0, FACE, 0.0),
    // 6 - bottom (-y)
    Triple(-PIP, -FACE, -PIP), Triple(-PIP, -FACE, 0.0), Triple(-PIP, -FACE, PIP),
    Triple(PIP, -FACE, -PIP), Triple(PIP, -FACE, 0.0), Triple(PIP, -FACE, PIP),
    // 3 - right (+x)
    Triple(FACE, -PIP, -PIP), Triple(FACE, 0.0, 0.0), Triple(FACE, PIP, PIP),
    // 4 - left (-x)
    Triple(-FACE, -PIP, -PIP), Triple(-FACE, -PIP, PIP), Triple(-FACE, PIP, -PIP), Triple(-FACE, PIP, PIP),
    // 2 - front (+z)
    Triple(-PIP, PIP, FACE), Triple(PIP, -PIP, FACE),
    // 5 - back (-z)
    Triple(-PIP, -PIP, -FACE), Triple(-PIP, PIP, -FACE), Triple(0.0, 0.0, -FACE),
    Triple(PIP, -PIP, -FACE), Triple(PIP, PIP, -FACE),
)

private fun pip(centre: Triple<Double, Double, Double>): Sphere = Sphere().apply {
    transform = translation(centre.first, centre.second, centre.third) * scaling(PIP_RADIUS)
}

private fun die(): Csg {
    val roundedCube = Csg("intersection", Cube(), Sphere().apply { transform = scaling(1.5, 1.5, 1.5) })
    val pips = pipCentres.map { pip(it) as Shape }.reduce { acc, s -> Csg("union", acc, s) }
    return Csg("difference", roundedCube, pips)
}

private fun dieWorld(): World {
    val w = World()

    val floor = Plane().apply {
        material = Material().apply {
            pattern = CheckersPattern(Color(0.25), Color(0.7))
            specular = 0.0
            reflective = 0.15
        }
    }

    val backWall = Plane().apply {
        transform = translation(0, 0, 8) * rotationX(Math.PI / 2.0)
        material = Material().apply {
            pattern = CheckersPattern(Color.fromString("3B5B92"), Color.fromString("DDE7F5"))
            specular = 0.0
        }
    }

    val theDie = die().apply {
        transform = translation(0.0, 1.0, 0.0) * rotationY(-0.6)
    }
    applyMaterial(
        theDie,
        Material().apply {
            color = Color(0.92, 0.90, 0.83)
            diffuse = 0.7
            specular = 0.3
            shininess = 120.0
            reflective = 0.05
        },
    )

    w.objects.addAll(listOf(floor, backWall, theDie))
    w.light = PointLight(point(3.0, 7.0, -5.0), Color.WHITE)
    return w
}

private fun render(fileName: String, width: Int, height: Int) {
    val camera = Camera(width, height, Math.PI / 3.0).apply {
        transform = viewTransform(point(2.5, 3.0, -5.0), point(0.0, 0.7, 0.0))
    }
    lateinit var canvas: Canvas
    val timeInMillis = measureTimeMillis {
        canvas = dieWorld().render(camera)
    }
    PNG.save(canvas, fileName)
    println("rendering took: ${timeInMillis / 1000.0} seconds")
}

internal fun csgDie(width: Int = 600, height: Int = 400) {
    render(prefixFileName("csgdie.png"), width, height)
}

fun main() {
    csgDie()
}
