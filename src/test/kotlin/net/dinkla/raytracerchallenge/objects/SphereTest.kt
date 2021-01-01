package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.World.Companion.defaultWorld
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class SphereTest {

    val w = defaultWorld()

    @Test
    fun `equals compares material positive`() {
        val s1 = Sphere()
        s1.material.color = Color(0.8, 1.0, 0.6)
        s1.material.diffuse = 0.7
        s1.material.specular = 0.2

        assertEquals(w.objects[0], s1)
    }

    @Test
    fun `equals compares material negative`() {
        val s1 = Sphere()
        s1.material.color = Color(0.8, 1.0, 0.6)
        s1.material.diffuse = 0.7
        s1.material.specular = 1.0

        assertNotEquals(w.objects[0], s1)
    }

    @Test
    fun `equals compares transform positive`() {
        val s2 = Sphere()
        s2.transform = Transformation.scaling(0.5, 0.5, 0.5)

        assertEquals(w.objects[1], s2)
    }

    @Test
    fun `equals compares transform negative`() {
        val s2 = Sphere()
        s2.transform = Transformation.scaling(1.5, 0.5, 0.5)

        assertNotEquals(w.objects[1], s2)
    }
}