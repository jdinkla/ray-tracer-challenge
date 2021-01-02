package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.StripePattern
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
    fun m_ambient_eq(double1: Double?) {
        assertEquals(double1!!, m.ambient)
    }

    @Then("m.diffuse = {double}")
    fun m_diffuse_eq(double1: Double?) {
        assertEquals(double1!!, m.diffuse)
    }

    @Then("m.specular = {double}")
    fun m_specular_eq(double1: Double?) {
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
        result = lighting(m, light, position, eyev,  normalv, shape = null)
    }

    @Then("result = color\\({double}, {double}, {double})")
    fun result_color(double1: Double?, double2: Double?, double3: Double?) {
        val expected = Color(double1!!, double2!!, double3!!)
        assertEquals(expected, result)
    }

    var inShadow: Boolean = false

    @Given("in_shadow ← true")
    fun in_shadow_true() {
        inShadow = true
    }

    @When("result ← lighting\\(m, light, position, eyev, normalv, in_shadow)")
    fun result_lighting_m_light_position_eyev_normalv_in_shadow() {
        result = lighting(m, light, position, eyev, normalv, inShadow, shape = null)
    }

    @Given("m.pattern ← stripe_pattern\\(white, black)")
    fun m_pattern_stripe_pattern_black_white() {
        m.pattern = StripePattern(white, black)
    }

    @Given("m.ambient ← {double}")
    fun m_ambient(double1: Double?) {
        m.ambient = double1!!
    }

    @Given("m.diffuse ← {double}")
    fun m_diffuse(double1: Double?) {
        m.diffuse = double1!!
    }

    @Given("m.specular ← {double}")
    fun m_specular(double1: Double?) {
        m.specular = double1!!
    }

    @Given("p1 ← point\\({double}, {double}, {double})")
    fun p1_point(double1: Double?, double2: Double?, double3: Double?) {
        p1 = point(double1!!, double2!!, double3!!)
    }

    @When("c1 ← lighting\\(m, light, p1, eyev, normalv, false)")
    fun c1_lighting_m_light_p1_eyev_normalv_false() {
       c1 = lighting(m, light, p1, eyev, normalv, false, shape = null)
    }

    @When("c2 ← lighting\\(m, light, p2, eyev, normalv, false)")
    fun c2_lighting_m_light_p2_eyev_normalv_false() {
        c2 = lighting(m, light, p2, eyev, normalv, false, shape = null)
    }

    @Then("c1 = color\\({int}, {int}, {int})")
    fun c1_color(int1: Int?, int2: Int?, int3: Int?) {
        val expected = Color(int1!!, int2!!, int3!!)
        assertEquals(expected, c1)
    }

    @Then("c2 = color\\({int}, {int}, {int})")
    fun c2_color(int1: Int?, int2: Int?, int3: Int?) {
        val expected = Color(int1!!, int2!!, int3!!)
        assertEquals(expected, c2)
    }


}
