package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.*
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.viewTransform
import org.junit.jupiter.api.Assertions.assertEquals

class ViewTransformationsStepDefinitions {

    @Given("from ← point\\({int}, {int}, {int})")
    fun from_point(int1: Int?, int2: Int?, int3: Int?) {
        from = point(int1!!, int2!!, int3!!)
    }

    @Given("to ← point\\({int}, {int}, {int})")
    fun to_point(int1: Int?, int2: Int?, int3: Int?) {
        to = point(int1!!, int2!!, int3!!)
    }

    @Given("up ← vector\\({int}, {int}, {int})")
    fun up_vector(int1: Int?, int2: Int?, int3: Int?) {
        up = vector(int1!!, int2!!, int3!!)
    }

    lateinit var t: Matrix

    @When("t ← view_transform\\(from, to, up)")
    fun t_view_transform_from_to_up() {
        t = viewTransform(from, to, up)
    }

    @Then("t = identity_matrix")
    fun t_identity_matrix() {
        assertEquals(identity4, t)
    }

    @Then("t = scaling\\({int}, {int}, {int})")
    fun t_scaling(int1: Int?, int2: Int?, int3: Int?) {
        val expected = Transformation.scaling(int1!!, int2!!, int3!!)
        assertEquals(expected, t)
    }

    @Then("t = translation\\({int}, {int}, {int})")
    fun t_translation(int1: Int?, int2: Int?, int3: Int?) {
        val expected = Transformation.translation(int1!!, int2!!, int3!!)
        assertEquals(expected, t)
    }

    @Then("t is the following 4x4 matrix:")
    fun t_is_the_following_4x4_matrix(dataTable: List<List<Double>>) {
        val expected = Matrix(4)
        expected.from(dataTable)
        assertEquals(expected, t)
    }

}


