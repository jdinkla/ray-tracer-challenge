package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import org.junit.jupiter.api.Assertions.assertEquals

class IntersectionsStepDefinitions {

    lateinit var i: Intersection

    @When("i ← intersection\\({double}, s)")
    fun i_intersection_s(double1: Double?) {
        i = Intersection(double1!!, s)
    }

    @Then("i.t = {double}")
    fun i_t(double1: Double?) {
        assertEquals(double1!!, i.t)
    }

    @Then("i.object = s")
    fun i_object_s() {
        assertEquals(s, i.`object`)
    }

    lateinit var i1: Intersection

    @Given("i1 ← intersection\\({int}, s)")
    fun i1_intersection_s(int1: Int?) {
        i1 = Intersection(int1!!.toDouble(), s)
    }

    lateinit var i2: Intersection

    @Given("i2 ← intersection\\({int}, s)")
    fun i2_intersection_s(int1: Int?) {
        i2 = Intersection(int1!!.toDouble(), s)
    }

    lateinit var iss: Intersections

    @When("is ← intersections\\(i1, i2)")
    fun is_intersections_i1_i2() {
        iss = Intersections(listOf(i1, i2))
    }

    @Then("is.count = {int}")
    fun is_count(int1: Int?) {
        assertEquals(int1!!, iss.count())
    }

    @Then("is[{int}].t = {int}")
    fun is_t(int1: Int?, int2: Int?) {
        assertEquals(int2!!.toDouble(), iss[int1!!].t, EPSILON)
    }
}
