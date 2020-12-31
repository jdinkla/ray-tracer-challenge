package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals

class SpheresStepDefinitions {

    lateinit var xs: List<Double>

    @Given("s ← sphere\\({string})")
    fun s_sphere(ignored: String?) {
        s = Sphere()
    }

    @When("xs ← intersect\\(s, r)")
    fun xs_intersect_s_r() {
        xs = s.intersect(r)
    }

    @Then("xs.count = {int}")
    fun xs_count(int1: Int?) {
        assertEquals(int1!!, xs.size)
    }

    @Then("xs[{int}] = {double}")
    fun xs(int1: Int?, double1: Double?) {
        assertEquals(xs[int1!!], double1!!, EPSILON)
    }

}
