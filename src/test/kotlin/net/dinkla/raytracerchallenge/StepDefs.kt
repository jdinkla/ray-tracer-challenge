package net.dinkla.raytracerchallenge

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.tuple
import net.dinkla.raytracerchallenge.math.vector
import org.junit.Assert.*

class StepDefs {

    lateinit var a: tuple
    lateinit var p: tuple
    lateinit var v: tuple

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

    @Then("a is not a point")
    fun a_is_not_a_point() {
        assertFalse(a.isPoint())
    }

    @Then("a is a vector")
    fun a_is_a_vector() {
        assertTrue(a.isVector())
    }

    @Given("p ← point\\({int}, {int}, {int})")
    fun p_point(int1: Int?, int2: Int?, int3: Int?) {
        p = point(int1!!, int2!!, int3!!)
    }

    @Then("p = tuple\\({int}, {int}, {int}, {int})")
    fun p_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        assertEquals(p, tuple(int1!!, int2!!, int3!!, int4!!))
    }

    @Given("v ← vector\\({int}, {int}, {int})")
    fun v_vector(int1: Int?, int2: Int?, int3: Int?) {
        v = vector(int1!!, int2!!, int3!!)
    }

    @Then("v = tuple\\({int}, {int}, {int}, {int})")
    fun v_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        assertEquals(v, tuple(int1!!, int2!!, int3!!, int4!!))
    }
}
