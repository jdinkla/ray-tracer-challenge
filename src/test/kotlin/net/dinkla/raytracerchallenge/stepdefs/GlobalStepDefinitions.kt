package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Plane

class GlobalStepDefinitions {

    @Given("p1 ← point\\({double}, {double}, {double})")
    fun p1_point(double1: Double?, double2: Double?, double3: Double?) {
        p1 = point(double1!!, double2!!, double3!!)
    }

    @Given("p2 ← point\\({double}, {double}, {double})")
    fun p2_point(double1: Double?, double2: Double?, double3: Double?) {
        p2 = point(double1!!, double2!!, double3!!)
    }

    @Given("p3 ← point\\({double}, {double}, {double})")
    fun p3_point(double1: Double?, double2: Double?, double3: Double?) {
        p3 = point(double1!!, double2!!, double3!!)
    }

    @Given("shape ← plane")
    fun shape_plane() {
        shape = Plane()
    }

    @Given("shape.material.ambient ← {int}")
    fun shape_material_ambient(int1: Int?) {
        shape.material.ambient = int1!!.toDouble()
    }

    @Given("direction ← vector\\({double}, {double}, {double})")
    fun direction_vector(double1: Double?, double2: Double?, double3: Double?) {
        direction = vector(double1!!, double2!!, double3!!)
    }

}