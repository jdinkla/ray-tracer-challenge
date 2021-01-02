package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import net.dinkla.raytracerchallenge.math.point

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

}