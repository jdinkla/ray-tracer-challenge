package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.World
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Tuple
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

fun tupleWith(s: String): Tuple {
    val parts = s.substring(1, s.length-1).split(",")
    val doubles = parts.map { it.toDouble() }
    assert(doubles.size == 3)
    return Tuple(doubles[0], doubles[1], doubles[2], 0.0)
}

fun colorWith(s: String): Color {
    val t = tupleWith(s)
    return Color(t.x, t.y, t.z)
}

fun transformWith(s: String): Matrix {
    val parts = s.split("(")
    return when (parts[0]) {
        "scaling" -> {
            val t = tupleWith("(" + parts[1])
            scaling(t.x, t.y, t.z)
        }
        else -> identity4
    }
}

fun Sphere.with(dataTable: List<List<String>>) {
    for (line in dataTable) {
        when (line[0]) {
            "material.color" -> material.color = colorWith(line[1])
            "material.diffuse" -> material.diffuse = line[1].toDouble()
            "material.specular" -> material.specular = line[1].toDouble()
            "transform" -> transform = transformWith(line[1])
        }
    }
}

class WorldStepDefinitions {

    lateinit var w: World

    @Given("w ← world")
    fun w_world() {
        w = World()
    }

    @Then("w contains no objects")
    fun w_contains_no_objects() {
        assertEquals(0, w.objects.size)
    }

    @Then("w has no light source")
    fun w_has_no_light_source() {
        assertEquals(0, w.objects.size)
    }

    lateinit var s1: Sphere

    @Given("s1 ← sphere with:")
    fun s1_sphere_with(dataTable: List<List<String>>) {
       s1 = Sphere()
       s1.with(dataTable)
    }

    lateinit var s2: Sphere

    @Given("s2 ← sphere with:")
    fun s2_sphere_with(dataTable: List<List<String>>) {
        s2 = Sphere()
        s2.with(dataTable)
    }

    @When("w ← defaultWorld")
    fun w_default_world() {
       w = World.defaultWorld()
    }

    @Then("w.light = light")
    fun w_light_light() {
       assertEquals(light, w.lights[0])
    }

    @Then("w contains s1")
    fun w_contains_s1() {
        assertTrue(s1 in w.objects)
    }

    @Then("w contains s2")
    fun w_contains_s2() {
        assertTrue(s1 in w.objects)
    }

    @When("xs ← intersect_world\\(w, r)")
    fun xs_intersect_world_w_r() {
        xs = w.intersect(r)
    }

}
