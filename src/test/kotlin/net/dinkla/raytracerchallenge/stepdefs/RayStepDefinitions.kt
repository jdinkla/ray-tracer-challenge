package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import org.junit.jupiter.api.Assertions.assertEquals

class RayStepDefinitions {

    @Given("origin ← point\\({double}, {double}, {double})")
    fun origin_point(double1: Double?, double2: Double?, double3: Double?) {
       origin = point(double1!!, double2!!, double3!!)
    }

    lateinit var direction: Point

    @Given("direction ← vector\\({int}, {int}, {int})")
    fun direction_vector(int1: Int?, int2: Int?, int3: Int?) {
        direction = vector(int1!!, int2!!, int3!!)
    }

    @When("r ← ray\\(origin, direction)")
    fun r_ray_origin_direction() {
        r = Ray(origin, direction)
    }

    @Then("r.origin = origin")
    fun r_origin_origin() {
       assertEquals(origin, r.origin)
    }

    @Then("r.direction = direction")
    fun r_direction_direction() {
        assertEquals(direction, r.direction)
    }

    @Then("position\\(r, {double}) = point\\({double}, {double}, {double})")
    fun position_r_point(double1: Double?, double2: Double?, double3: Double?, double4: Double?) {
        val expected = point(double2!!, double3!!, double4!!)
        assertEquals(expected, r.position(double1!!))
    }

    lateinit var m: Matrix

    @Given("m ← translation\\({int}, {int}, {int})")
    fun m_translation(int1: Int?, int2: Int?, int3: Int?) {
        m = translation(int1!!, int2!!, int3!!)
    }

    lateinit var r2: Ray

    @When("r2 ← transform\\(r, m)")
    fun r2_transform_r_m() {
       r2 = r.transform(m)
    }

    @Then("r2.origin = point\\({int}, {int}, {int})")
    fun r2_origin_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, r2.origin)
    }

    @Then("r2.direction = vector\\({int}, {int}, {int})")
    fun r2_direction_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        assertEquals(expected, r2.direction)
    }

    @Given("m ← scaling\\({int}, {int}, {int})")
    fun m_scaling(int1: Int?, int2: Int?, int3: Int?) {
        m = scaling(int1!!, int2!!, int3!!)
    }
}
