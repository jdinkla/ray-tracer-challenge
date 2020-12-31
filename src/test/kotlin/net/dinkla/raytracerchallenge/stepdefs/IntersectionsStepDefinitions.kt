package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertNull

class IntersectionsStepDefinitions {

    var i: Intersection? = null

    @When("i ← intersection\\({double}, s)")
    fun i_intersection_s(double1: Double?) {
        i = Intersection(double1!!, s)
    }

    @Then("i.t = {double}")
    fun i_t(double1: Double?) {
        assertEquals(double1!!, i!!.t)
    }

    @Then("i.object = s")
    fun i_object_s() {
        assertEquals(s, i!!.`object`)
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

    @When("xs ← intersections\\(i1, i2)")
    fun is_intersections_i1_i2() {
        xs = Intersections(i1, i2)
    }

    @Given("xs ← intersections\\(i2, i1)")
    fun xs_intersections_i2_i1() {
        xs = Intersections(i2, i1)
    }

    @When("i ← hit\\(xs)")
    fun i_hit_xs() {
        i = xs.hit()
    }

    @Then("i = i1")
    fun i_i1() {
        assertEquals(i1, i)
    }

    @Then("i = i2")
    fun i_i2() {
        assertEquals(i2, i)
    }

    @Then("i is nothing")
    fun i_is_nothing() {
        assertNull(i)
    }

    lateinit var i3: Intersection

    @Given("i3 ← intersection\\({int}, s)")
    fun i3_intersection_s(int1: Int?) {
        i3 = Intersection(int1!!.toDouble(), s)
    }

    lateinit var i4: Intersection

    @Given("i4 ← intersection\\({int}, s)")
    fun i4_intersection_s(int1: Int?) {
        i4 = Intersection(int1!!.toDouble(), s)
    }

    @Given("xs ← intersections\\(i1, i2, i3, i4)")
    fun xs_intersections_i1_i2_i3_i4() {
        xs = Intersections(i1, i2, i3, i4)
    }

    @Then("i = i4")
    fun i_i4() {
        assertEquals(i4, i)
    }

}
