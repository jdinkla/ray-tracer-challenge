package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.TestShape
import net.dinkla.raytracerchallenge.math.Transformation
import net.dinkla.raytracerchallenge.math.Transformation.rotationZ
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals

class ShapesStepDefinitions {

    @Given("s ← test_shape")
    fun s_test_shape() {
        s = TestShape()
    }

    @Given("transform = translation\\({int}, {int}, {int})")
    fun transform_translation(int1: Int?, int2: Int?, int3: Int?) {
        transform = Transformation.translation(int1!!, int2!!, int3!!)
    }

    @When("set_transform\\(s, transform)")
    fun set_transform_s_transform() {
        s.transform = transform
    }

    @Then("s.transform = translation\\({int}, {int}, {int})")
    fun s_transform_translation(int1: Int?, int2: Int?, int3: Int?) {
        val expected = Transformation.translation(int1!!, int2!!, int3!!)
        Assertions.assertEquals(expected, s.transform)
    }

    @Given("transform = scaling\\({int}, {int}, {int})")
    fun transform_scaling(int1: Int?, int2: Int?, int3: Int?) {
        transform = Transformation.scaling(int1!!, int2!!, int3!!)
    }

    @Then("s.saved_ray.origin = point\\({int}, {int}, {double})")
    fun s_saved_ray_origin_point(int1: Int?, int2: Int?, double1: Double?) {
        val expected = point(int1!!.toDouble(), int2!!.toDouble(), double1!!)
        assertEquals(expected, (s as TestShape).ray.origin)
    }

    @Then("s.saved_ray.direction = vector\\({int}, {int}, {double})")
    fun s_saved_ray_direction_vector(int1: Int?, int2: Int?, double1: Double?) {
        val expected = vector(int1!!.toDouble(), int2!!.toDouble(), double1!!)
        assertEquals(expected, (s as TestShape).ray.direction)
    }

    @When("n ← normal_at\\(s, p)")
    fun n_normal_at_s_p() {
        n = s.normal(p)
    }

    @Given("transform ← scaling\\({int}, {double}, {int}) * rotation_z\\({double})")
    fun transform_scaling_rotation_z(int1: Int?, double1: Double?, int2: Int?, double2: Double?) {
        transform = scaling(int1!!.toDouble(), double1!!, int2!!.toDouble()) * rotationZ(double2!!)
    }

}