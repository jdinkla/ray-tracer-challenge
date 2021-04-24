package net.dinkla.raytracerchallenge.stepdefs

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix.Companion.matrix
import net.dinkla.raytracerchallenge.math.Transformation
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UtilitiesKtTest {

    private val elem1 = listOf("material.color", "(0.8, 1.0, 0.6)")
    private val elem2 = listOf("material.diffuse", "0.7")
    private val elem3 = listOf("material.specular", "0.2")
    private val elem4 = listOf("material.reflective", "0.5")
    private val elem5 = listOf("transform", "translation(2, -1, 1.5)")

    val ls = listOf(elem1, elem2, elem3, elem4, elem5)

    @Test
    fun with() {
        val s = Sphere()
        s.with(ls)
        assertEquals(s.material.color, Color(0.8, 1.0, 0.6))
        assertEquals(s.material.diffuse, 0.7)
        assertEquals(s.material.specular, 0.2)
        assertEquals(s.material.reflective, 0.5)
        assertEquals(Transformation.translation(2.0, -1.0, 1.5), s.transform)
    }
}