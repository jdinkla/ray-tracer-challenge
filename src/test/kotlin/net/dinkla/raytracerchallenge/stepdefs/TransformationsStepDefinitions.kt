package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.shearing
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import org.junit.jupiter.api.Assertions.assertEquals

class TransformationsStepDefinitions {

    @Given("transform ← translation\\({int}, {int}, {int})")
    fun transform_translation(int1: Int?, int2: Int?, int3: Int?) {
        transform = translation(int1!!, int2!!, int3!!)
    }

    @Then("transform * p = point\\({int}, {int}, {int})")
    fun transform_p_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, transform * p)
    }

    lateinit var inv : Matrix

    @Given("inv ← inverse\\(transform)")
    fun inv_inverse_transform() {
        inv = transform.inverse()
    }

    @Then("transform * v = v")
    fun transform_v_v() {
       assertEquals(v, transform * v)
    }

    @Given("transform ← scaling\\({int}, {int}, {int})")
    fun transform_scaling(int1: Int?, int2: Int?, int3: Int?) {
        transform = scaling(int1!!, int2!!, int3!!)
    }

    @Then("transform * v = vector\\({int}, {int}, {int})")
    fun transform_v_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        assertEquals(expected, transform * v)
    }

    @Then("inv * v = vector\\({int}, {int}, {int})")
    fun inv_v_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        assertEquals(expected, inv * v)
    }

    lateinit var half_quarter: Matrix

    @Given("half_quarter ← rotation_x\\(π \\/ {int})")
    fun half_quarter_rotation_x_π(int1: Int?) {
        half_quarter = rotationX(Math.PI / int1!!.toDouble())
    }

    lateinit var full_quarter: Matrix

    @Given("full_quarter ← rotation_x\\(π \\/ {int})")
    fun full_quarter_rotation_x_π(int1: Int?) {
        full_quarter = rotationX(Math.PI / int1!!.toDouble())
    }

    @Then("half_quarter * p = point\\({double}, {double}, {double})")
    fun half_quarter_p_point(double1: Double?, double2: Double?, double3: Double?) {
        val expected = point(double1!!, double2!!, double3!!)
        assertEquals(expected, half_quarter * p)
    }

    @Then("full_quarter * p = point\\({int}, {int}, {int})")
    fun full_quarter_p_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, full_quarter * p)
    }

    @Given("inv ← inverse\\(half_quarter)")
    fun inv_inverse_half_quarter() {
        inv = half_quarter.inverse()
    }

    @Then("inv * p = point\\({double}, {double}, {double})")
    fun inv_p_point(double1: Double?, double2: Double?, double3: Double?) {
        val expected = point(double1!!, double2!!, double3!!)
        assertEquals(expected, inv * p)
    }

    @Given("half_quarter ← rotation_y\\(π \\/ {int})")
    fun half_quarter_rotation_y_π(int1: Int?) {
        half_quarter = rotationY(Math.PI / int1!!.toDouble())
    }

    @Given("full_quarter ← rotation_y\\(π \\/ {int})")
    fun full_quarter_rotation_y_π(int1: Int?) {
        full_quarter = rotationY(Math.PI / int1!!.toDouble())
    }

    @Given("half_quarter ← rotation_z\\(π \\/ {int})")
    fun half_quarter_rotation_z_π(int1: Int?) {
        half_quarter = rotationZ(Math.PI / int1!!.toDouble())
    }

    @Given("full_quarter ← rotation_z\\(π \\/ {int})")
    fun full_quarter_rotation_z_π(int1: Int?) {
        full_quarter = rotationZ(Math.PI / int1!!.toDouble())
    }

    @Given("transform ← shearing\\({int}, {int}, {int}, {int}, {int}, {int})")
    fun transform_shearing(int1: Int?, int2: Int?, int3: Int?, int4: Int?, int5: Int?, int6: Int?) {
        transform = shearing(int1!!.toDouble(), int2!!.toDouble(), int3!!.toDouble(), int4!!.toDouble(), int5!!.toDouble(), int6!!.toDouble())
    }

    lateinit var a: Matrix

    @Given("A ← rotation_x\\(π \\/ {int})")
    fun a_rotation_x_π(int1: Int?) {
        a = rotationX(Math.PI / int1!!.toDouble())
    }

    lateinit var b: Matrix

    @Given("B ← scaling\\({int}, {int}, {int})")
    fun b_scaling(int1: Int?, int2: Int?, int3: Int?) {
        b = scaling(int1!!, int2!!, int3!!)
    }

    lateinit var c: Matrix

    @Given("C ← translation\\({int}, {int}, {int})")
    fun c_translation(int1: Int?, int2: Int?, int3: Int?) {
        c = translation(int1!!, int2!!, int3!!)
    }

    @When("p2 ← A * p")
    fun p2_a_p() {
        p2 = a * p
    }

    @Then("p2 = point\\({int}, {int}, {int})")
    fun p2_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, p2)
    }

    @When("p3 ← B * p2")
    fun p3_b_p2() {
        p3 = b * p2
    }

    @Then("p3 = point\\({int}, {int}, {int})")
    fun p3_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, p3)
    }

    lateinit var p4: Point

    @When("p4 ← C * p3")
    fun p4_c_p3() {
        p4 = c * p3
    }

    @Then("p4 = point\\({int}, {int}, {int})")
    fun p4_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, p4)
    }

    @When("T ← C * B * A")
    fun t_c_b_a() {
        t = c * b * a
    }

    @Then("T * p = point\\({int}, {int}, {int})")
    fun t_p_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, t * p)
    }
}