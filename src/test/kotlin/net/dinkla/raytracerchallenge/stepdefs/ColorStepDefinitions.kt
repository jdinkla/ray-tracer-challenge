package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Color
import org.junit.jupiter.api.Assertions.assertEquals

class ColorStepDefinitions {

    lateinit var c: Color

    @Given("c ← color\\({double}, {double}, {double})")
    fun c_color(double1: Double?, double2: Double?, double3: Double?) {
        c = Color(double1!!, double2!!, double3!!)
    }

    @Then("c.red = {double}")
    fun c_red(double1: Double?) {
        assertEquals(double1!!, c.red, EPSILON)
    }

    @Then("c.green = {double}")
    fun c_green(double1: Double?) {
        assertEquals(double1!!, c.green, EPSILON)
    }

    @Then("c.blue = {double}")
    fun c_blue(double1: Double?) {
        assertEquals(double1!!, c.blue, EPSILON)
    }

    @Given("c1 ← color\\({double}, {double}, {double})")
    fun c1_color(double1: Double?, double2: Double?, double3: Double?) {
        c1 = Color(double1!!, double2!!, double3!!)
    }

    @Given("c2 ← color\\({double}, {double}, {double})")
    fun c2_color(double1: Double?, double2: Double?, double3: Double?) {
        c2 = Color(double1!!, double2!!, double3!!)
    }

    @Then("c1 + c2 = color\\({double}, {double}, {double})")
    fun c1_c2_color(double1: Double?, double2: Double?, double3: Double?) {
        assertEquals(c1 + c2, Color(double1!!, double2!!, double3!!))
    }

    @Given("c3 ← color\\({double}, {double}, {double})")
    fun c3_color(double1: Double?, double2: Double?, double3: Double?) {
        c3 = Color(double1!!, double2!!, double3!!)
    }

    @Then("c1 - c3 = color\\({double}, {double}, {double})")
    fun c1_c3_color(double1: Double?, double2: Double?, double3: Double?) {
        assertEquals(c1 - c3, Color(double1!!, double2!!, double3!!))
    }

    @Then("c * {double} = color\\({double}, {double}, {double})")
    fun c_color(double1: Double?, double2: Double?, double3: Double?, double4: Double?) {
        assertEquals(c * double1!!, Color(double2!!, double3!!, double4!!))
    }

    @Given("c4 ← color\\({double}, {double}, {double})")
    fun c4_color(double1: Double?, double2: Double?, double3: Double?) {
        c4 = Color(double1!!, double2!!, double3!!)
    }

    @Then("c1 * c4 = color\\({double}, {double}, {double})")
    fun c1_c4_color(double1: Double?, double2: Double?, double3: Double?) {
        assertEquals(c1 * c4, Color(double1!!, double2!!, double3!!))
    }

}
