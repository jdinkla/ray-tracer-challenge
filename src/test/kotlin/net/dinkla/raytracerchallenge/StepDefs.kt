package net.dinkla.raytracerchallenge

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.tuple
import org.junit.Assert.*

class StepDefs {

    lateinit var a: tuple

    @Given("a ← tuple\\({double}, {double}, {double}, {double})")
    fun a_tuple(double1: Double?, double2: Double?, double3: Double?, double4: Double?) {
        a = tuple(double1!!, double2!!, double3!!, double4!!)
    }

    @Then("a.x = {double}")
    fun a_x(double1: Double?) {
        assertEquals(double1!!, a.x, 0.00001)
    }

    @Then("a.y = {double}")
    fun a_y(double1: Double?) {
        assertEquals(double1!!, a.y, 0.00001)
    }

    @Then("a.z = {double}")
    fun a_z(double1: Double?) {
        assertEquals(double1!!, a.z, 0.00001)
    }

    @Then("a.w = {double}")
    fun a_w(double1: Double?) {
        assertEquals(double1!!, a.w, 0.00001)
    }

    @Then("a is a point")
    fun a_is_a_point() {
        assertTrue(a.isPoint())
    }

    @Then("a is not a vector")
    fun a_is_not_a_vector() {
        assertFalse(a.isVector())
    }
}
