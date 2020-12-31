package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Transformation
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals

class SpheresStepDefinitions {

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
        assertEquals(int1!!, xs.count())
    }

    @Then("xs[{int}].t = {double}")
    fun xs_t(int1: Int?, double1: Double?) {
        assertEquals(xs[int1!!].t, double1!!, EPSILON)
    }

    @Then("xs[{int}].object = s")
    fun xs_object_s(int1: Int?) {
        assertEquals(xs[int1!!].`object`, s)
    }

    @Then("s.transform = identity_matrix")
    fun s_transform_identity_matrix() {
        assertEquals(identity4, s.transform)
    }

    lateinit var t: Matrix

    @Given("t ← translation\\({int}, {int}, {int})")
    fun t_translation(int1: Int?, int2: Int?, int3: Int?) {
        t = translation(int1!!, int2!!, int3!!)
    }

    @Then("s.transform = t")
    fun s_transform_t() {
        assertEquals(t, s.transform)
    }

    @When("setTransform\\(s, t)")
    fun set_transform_s_transform() {
        s.transform = t
    }

    @Given("t ← scaling\\({int}, {int}, {int})")
    fun t_scaling(int1: Int?, int2: Int?, int3: Int?) {
        t = scaling(int1!!, int2!!, int3!!)
    }
}
