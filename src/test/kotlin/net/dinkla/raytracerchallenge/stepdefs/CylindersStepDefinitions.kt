package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Cylinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse

class CylindersStepDefinitions {

    private lateinit var cyl: Cylinder

    @Given("cyl ← cylinder\\()")
    fun cyl_cylinder() {
        cyl = Cylinder()
    }

    @When("xs ← local_intersect\\(cyl, r)")
    fun xs_local_intersect_cyl_r() {
        xs = cyl.intersectInObjectSpace(r)
    }

    @When("n ← local_normal_at\\(cyl, point\\({double}, {double}, {double}))")
    fun n_local_normal_at_cyl_point(double1: Double?, double2: Double?, double3: Double?) {
        n = cyl.normalInObjectSpace(point(double1!!, double2!!, double3!!))
    }

    @Then("cyl.minimum = -infinity")
    fun cyl_minimum_neg_infinity() {
        assertEquals(Double.NEGATIVE_INFINITY, cyl.minimum)
    }

    @Then("cyl.maximum = infinity")
    fun cyl_maximum_infinity() {
        assertEquals(Double.POSITIVE_INFINITY, cyl.maximum)
    }

    @Given("cyl.minimum ← {int}")
    fun cyl_minimum_set(int1: Int?) {
        cyl.minimum = int1!!.toDouble()
    }

    @Given("cyl.maximum ← {int}")
    fun cyl_maximum_set(int1: Int?) {
        cyl.maximum = int1!!.toDouble()
    }

    @Then("cyl.closed = false")
    fun cyl_closed_false() {
        assertFalse(cyl.closed)
    }

    @Given("cyl.closed ← true")
    fun cyl_closed_true() {
        cyl.closed = true
    }
}
