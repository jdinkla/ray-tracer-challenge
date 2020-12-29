package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.tuple
import net.dinkla.raytracerchallenge.math.vector
import org.junit.Assert.*
import kotlin.math.sqrt

val EPSILON: Double = 0.000001

class TuplesStepDefinitions {

    lateinit var a: tuple
    lateinit var p: tuple
    lateinit var v: tuple
    lateinit var a1: tuple
    lateinit var a2: tuple

    @Given("a ← tuple\\({double}, {double}, {double}, {double})")
    fun a_tuple(double1: Double?, double2: Double?, double3: Double?, double4: Double?) {
        a = tuple(double1!!, double2!!, double3!!, double4!!)
    }

    @Then("a.x = {double}")
    fun a_x(double1: Double?) {
        assertEquals(double1!!, a.x, EPSILON)
    }

    @Then("a.y = {double}")
    fun a_y(double1: Double?) {
        assertEquals(double1!!, a.y, EPSILON)
    }

    @Then("a.z = {double}")
    fun a_z(double1: Double?) {
        assertEquals(double1!!, a.z, EPSILON)
    }

    @Then("a.w = {double}")
    fun a_w(double1: Double?) {
        assertEquals(double1!!, a.w, EPSILON)
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

    @Given("a1 ← tuple\\({int}, {int}, {int}, {int})")
    fun a1_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        a1 = tuple(int1!!, int2!!, int3!!, int4!!)
    }

    @Given("a2 ← tuple\\({int}, {int}, {int}, {int})")
    fun a2_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        a2 = tuple(int1!!, int2!!, int3!!, int4!!)
    }

    @Then("a1 + a2 = tuple\\({int}, {int}, {int}, {int})")
    fun a1_a2_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        assertEquals(a1 + a2, tuple(int1!!, int2!!, int3!!, int4!!))
     }

    lateinit var p1: tuple
    lateinit var p2: tuple

    @Given("p1 ← point\\({int}, {int}, {int})")
    fun p1_point(int1: Int?, int2: Int?, int3: Int?) {
        p1 = point(int1!!, int2!!, int3!!)
    }

    @Given("p2 ← point\\({int}, {int}, {int})")
    fun p2_point(int1: Int?, int2: Int?, int3: Int?) {
        p2 = point(int1!!, int2!!, int3!!)
    }

    @Then("p1 - p2 = vector\\({int}, {int}, {int})")
    fun p1_p2_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(p1 - p2, vector(int1!!, int2!!, int3!!))
    }

    @Then("p - v = point\\({int}, {int}, {int})")
    fun p_v_point(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(p - v, point(int1!!, int2!!, int3!!))
    }

    lateinit var v1: tuple
    lateinit var v2: tuple

    @Given("v1 ← vector\\({int}, {int}, {int})")
    fun v1_vector(int1: Int?, int2: Int?, int3: Int?) {
        v1 = vector(int1!!, int2!!, int3!!)
    }

    @Given("v2 ← vector\\({int}, {int}, {int})")
    fun v2_vector(int1: Int?, int2: Int?, int3: Int?) {
        v2 = vector(int1!!, int2!!, int3!!)
    }

    @Then("v1 - v2 = vector\\({int}, {int}, {int})")
    fun v1_v2_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(v1 - v2, vector(int1!!, int2!!, int3!!))
    }

    lateinit var zero: tuple

    @Given("zero ← vector\\({int}, {int}, {int})")
    fun zero_vector(int1: Int?, int2: Int?, int3: Int?) {
        zero = vector(int1!!, int2!!, int3!!)
    }

    @Then("zero - v = vector\\({int}, {int}, {int})")
    fun zero_v_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(zero - v, vector(int1!!, int2!!, int3!!))
    }

    @Then("-a = tuple\\({int}, {int}, {int}, {int})")
    fun a_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        assertEquals(-a, tuple(int1!!, int2!!, int3!!, int4!!))
    }

    @Then("a * {double} = tuple\\({double}, {double}, {double}, {double})")
    fun a_tuple(double1: Double?, double2: Double?, double3: Double?, double4: Double?, double5: Double?) {
        assertEquals(a*double1!!, tuple(double2!!, double3!!, double4!!, double5!!))
    }

    lateinit var ad: tuple

    @Given("ad ← tuple\\({int}, {int}, {int}, {int})")
    fun ad_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        ad = tuple(int1!!, int2!!, int3!!, int4!!)
    }

    @Then("ad / {double} = tuple\\({double}, {double}, {double}, {double})")
    fun ad_tuple(double1: Double?, double2: Double?, double3: Double?, double4: Double?, double5: Double?) {
        assertEquals(ad/double1!!, tuple(double2!!, double3!!, double4!!, double5!!))
    }

    @Then("magnitude\\(v2) = {int}")
    fun magnitude_v2(int1: Int?) {
        assertEquals(sqrt(int1!!.toDouble()), v2.magnitude(), EPSILON)
    }

    @Then("magnitude\\(v) = √{int}")
    fun magnitude_v(int1: Int?) {
        assertEquals(sqrt(int1!!.toDouble()), v.magnitude(), EPSILON)
    }

    @Given("a ← vector\\({int}, {int}, {int})")
    fun a_vector(int1: Int?, int2: Int?, int3: Int?) {
        a = vector(int1!!, int2!!, int3!!)
    }

    lateinit var b: tuple

    @Given("b ← vector\\({int}, {int}, {int})")
    fun b_vector(int1: Int?, int2: Int?, int3: Int?) {
        b = vector(int1!!, int2!!, int3!!)
    }

    @Then("dot\\(a, b) = {int}")
    fun dot_a_b(int1: Int?) {
        assertEquals(int1!!.toDouble(), a dot b, EPSILON)
    }

    @Then("cross\\(a, b) = vector\\({int}, {int}, {int})")
    fun cross_a_b_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(a cross b, vector(int1!!, int2!!, int3!!))
    }

    @Then("cross\\(b, a) = vector\\({int}, {int}, {int})")
    fun cross_b_a_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(b cross a, vector(int1!!, int2!!, int3!!))
    }


}