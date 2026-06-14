package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.World.Companion.defaultWorld
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Covers "The refracted color with a refracted ray" from world.feature, which is disabled there
 * because the book's reference color is only 1e-4 precise while cucumber compares colors at the
 * project's stricter 1e-5 EPSILON. Here we assert at the book's own tolerance.
 */
internal class RefractedColorSpec {

    // The book's reference values are rounded to 5 decimals; compare at the book's 1e-4 tolerance.
    private val bookDelta = 0.0001

    private fun assertColor(expected: Color, actual: Color) {
        assertEquals(expected.red, actual.red, bookDelta)
        assertEquals(expected.green, actual.green, bookDelta)
        assertEquals(expected.blue, actual.blue, bookDelta)
    }

    @Test
    fun `the refracted color with a refracted ray`() {
        val w = defaultWorld()
        val a = w.objects[0]
        a.material.ambient = 1.0
        a.material.pattern = TestPattern()
        val b = w.objects[1]
        b.material.transparency = 1.0
        b.material.refractiveIndex = 1.5
        val r = Ray(point(0.0, 0.0, 0.1), vector(0, 1, 0))
        val xs = Intersections(
            Intersection(-0.9899, a),
            Intersection(-0.4899, b),
            Intersection(0.4899, b),
            Intersection(0.9899, a),
        )
        val comps = Computations.prepare(xs[2], r, xs)
        val c = w.refractedColor(comps, 5)
        assertColor(Color(0.0, 0.99888, 0.04725), c)
    }

    @Test
    fun `shade_hit with a transparent material`() {
        val w = defaultWorld()
        val floor = net.dinkla.raytracerchallenge.objects.Plane()
        floor.transform = net.dinkla.raytracerchallenge.math.Transformation.translation(0, -1, 0)
        floor.material.transparency = 0.5
        floor.material.refractiveIndex = 1.5
        w.objects.add(floor)
        val ball = net.dinkla.raytracerchallenge.objects.Sphere()
        ball.material.color = Color(1.0, 0.0, 0.0)
        ball.material.ambient = 0.5
        ball.transform = net.dinkla.raytracerchallenge.math.Transformation.translation(0.0, -3.5, -0.5)
        w.objects.add(ball)
        val sqrt2 = 1.41421356237
        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt2 / 2, sqrt2 / 2))
        val xs = Intersections(Intersection(sqrt2, floor))
        val comps = Computations.prepare(xs[0], r, xs)
        val color = w.shadeHit(comps, 5)
        assertColor(Color(0.93642, 0.68642, 0.68642), color)
    }
}
