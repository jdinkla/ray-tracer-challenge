package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.lighting
import net.dinkla.raytracerchallenge.math.*
import org.junit.jupiter.api.Assertions.assertEquals

class MaterialsStepDefinitions {

    @Given("m ← material")
    fun m_material() {
        m = Material()
    }

    @Then("m.color = color\\({int}, {int}, {int})")
    fun m_color_color(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(Color(int1!!, int2!!, int3!!), m.color)
    }

    @Then("m.ambient = {double}")
    fun m_ambient(double1: Double?) {
        assertEquals(double1!!, m.ambient)
    }

    @Then("m.diffuse = {double}")
    fun m_diffuse(double1: Double?) {
        assertEquals(double1!!, m.diffuse)
    }

    @Then("m.specular = {double}")
    fun m_specular(double1: Double?) {
        assertEquals(double1!!, m.specular)
    }

    @Then("m.shininess = {double}")
    fun m_shininess(double1: Double?) {
        assertEquals(double1!!, m.shininess)
    }

    lateinit var eyev: Vector

    @Given("eyev ← vector\\({double}, {double}, {double})")
    fun eyev_vector(double1: Double?, double2: Double?, double3: Double?) {
        eyev = vector(double1!!, double2!!, double3!!)
    }

    lateinit var normalv: Vector

    @Given("normalv ← vector\\({int}, {int}, {int})")
    fun normalv_vector(int1: Int?, int2: Int?, int3: Int?) {
        normalv = vector(int1!!, int2!!, int3!!)
    }

    lateinit var pos: Point

    @Given("pos ← point\\({int}, {int}, {int})")
    fun pos_point(int1: Int?, int2: Int?, int3: Int?) {
        pos = point(int1!!, int2!!, int3!!)
    }

    @Given("light ← point_light\\(pos, intensity)")
    fun light_point_light_p_c() {
        light = PointLight(pos, intensity)
    }

    lateinit var result: Color

    @When("result ← lighting\\(m, light, position, eyev, normalv)")
    fun result_lighting_m_light_position_eyev_normalv() {
        result = lighting(m, light, position, eyev,  normalv)
    }

    @Then("result = color\\({double}, {double}, {double})")
    fun result_color(double1: Double?, double2: Double?, double3: Double?) {
        val expected = Color(double1!!, double2!!, double3!!)
        assertEquals(expected, result)
    }

}
