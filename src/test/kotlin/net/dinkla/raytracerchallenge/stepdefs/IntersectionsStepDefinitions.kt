package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Intersection
import io.cucumber.java.en.Then
import kotlin.test.assertEquals


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


}
