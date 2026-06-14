package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Cone

class ConesStepDefinitions {

    private val cone: Cone
        get() = shape as Cone

    @Given("shape ← cone\\()")
    fun shape_cone() {
        shape = Cone()
    }

    @When("xs ← local_intersect\\(shape, r)")
    fun xs_local_intersect_shape_r() {
        xs = shape.intersectInObjectSpace(r)
    }

    @When("n ← local_normal_at\\(shape, point\\({double}, {double}, {double}))")
    fun n_local_normal_at_shape_point(double1: Double?, double2: Double?, double3: Double?) {
        n = shape.normalInObjectSpace(point(double1!!, double2!!, double3!!))
    }

    @Given("shape.minimum ← {double}")
    fun shape_minimum_set(double1: Double?) {
        cone.minimum = double1!!
    }

    @Given("shape.maximum ← {double}")
    fun shape_maximum_set(double1: Double?) {
        cone.maximum = double1!!
    }

    @Given("shape.closed ← true")
    fun shape_closed_true() {
        cone.closed = true
    }
}
