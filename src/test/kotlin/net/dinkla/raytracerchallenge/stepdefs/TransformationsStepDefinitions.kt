package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Transformation.rotationX
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import kotlin.math.exp
import kotlin.test.assertEquals

class TransformationsStepDefinitions {

    lateinit var transform : Matrix

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

}