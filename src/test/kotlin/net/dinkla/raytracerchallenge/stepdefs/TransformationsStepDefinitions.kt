package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
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

    @Then("inv * p = point\\({int}, {int}, {int})")
    fun inv_p_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, inv * p)
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

}