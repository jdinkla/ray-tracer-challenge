package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

/**
 * Covers the two parent-space normal scenarios disabled in shapes.feature: the book's expected
 * normal (0.2857, 0.4286, -0.8571) is rounded to 4 decimals and unsatisfiable at the project's
 * 1e-5 compare, so we assert at the book's 1e-4 tolerance here.
 */
internal class GroupSpec {

    private val bookDelta = 0.0001

    // g1[rotY(90°)] -> g2[scale(1,2,3)] -> s[translate(5,0,0)]
    private fun nestedSphere(): Sphere {
        val g1 = Group().apply { transform = rotationY(Math.PI / 2.0) }
        val g2 = Group().apply { transform = scaling(1, 2, 3) }
        g1.addChild(g2)
        val s = Sphere().apply { transform = translation(5, 0, 0) }
        g2.addChild(s)
        return s
    }

    private fun assertVector(expected: Vector, actual: Vector) {
        assertEquals(expected.x, actual.x, bookDelta)
        assertEquals(expected.y, actual.y, bookDelta)
        assertEquals(expected.z, actual.z, bookDelta)
    }

    @Test
    fun `converting a normal from object to world space`() {
        val s = nestedSphere()
        val r = sqrt(3.0) / 3.0
        val n = s.normalToWorld(vector(r, r, r))
        assertVector(vector(0.2857, 0.4286, -0.8571), n)
    }

    @Test
    fun `finding the normal on a child object`() {
        val s = nestedSphere()
        val n = s.normal(point(1.7321, 1.1547, -5.5774))
        assertVector(vector(0.2857, 0.4286, -0.8571), n)
    }
}
