package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Plane
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals

class PlanesStepDefinitions {

    lateinit var pl: Plane

    @Given("pl ← plane")
    fun pl_plane() {
        pl = Plane()
    }

    lateinit var n1: Vector

    @When("n1 ← local_normal_at\\(pl, p)")
    fun n1_local_normal_at_pl_p() {
        n1 = pl.normalInObjectSpace(p)
    }

    lateinit var n2: Vector

    @When("n2 ← local_normal_at\\(pl, p)")
    fun n2_local_normal_at_pl_p() {
        n2 = pl.normalInObjectSpace(p)
    }

    lateinit var n3: Vector

    @When("n3 ← local_normal_at\\(pl, p)")
    fun n3_local_normal_at_pl_p() {
        n3 = pl.normalInObjectSpace(p)
    }

    @Then("n1 = vector\\({int}, {int}, {int})")
    fun n1_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        Assertions.assertEquals(expected, n1)
    }

    @Then("n2 = vector\\({int}, {int}, {int})")
    fun n2_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        Assertions.assertEquals(expected, n2)
    }

    @Then("n3 = vector\\({int}, {int}, {int})")
    fun n3_vector(int1: Int?, int2: Int?, int3: Int?) {
        val expected = vector(int1!!, int2!!, int3!!)
        Assertions.assertEquals(expected, n3)
    }

    @When("xs ← local_intersect\\(p, r)")
    fun xs_local_intersect_p_r() {
        xs = pl.intersectInObjectSpace(r)
    }

    @Then("xs is empty")
    fun xs_is_empty() {
        assertTrue(xs.count() == 0)
    }

    @Then("xs[{int}].object = pl")
    fun xs_object_pl(int1: Int?) {
        assertEquals(pl, xs[int1!!].`object`)
    }

}