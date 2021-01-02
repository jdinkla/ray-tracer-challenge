package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.StripePattern
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.Tuple
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.Sphere
import kotlin.test.assertEquals

class PatternsStepDefinitions {

    @Given("black ← color\\({int}, {int}, {int})")
    fun black_color(int1: Int?, int2: Int?, int3: Int?) {
        black = Color(int1!!, int2!!, int3!!)
    }

    @Given("white ← color\\({int}, {int}, {int})")
    fun white_color(int1: Int?, int2: Int?, int3: Int?) {
        white = Color(int1!!, int2!!, int3!!)
    }

    lateinit var pattern: StripePattern

    @Given("pattern ← stripe_pattern\\(white, black)")
    fun pattern_stripe_pattern_white_black() {
       pattern = StripePattern(white, black)
    }

    @Then("pattern.a = white")
    fun pattern_a_white() {
        assertEquals(white, pattern.a)
    }

    @Then("pattern.b = black")
    fun pattern_b_black() {
        assertEquals(black, pattern.b)
    }

    @Given("p3 ← point\\({int}, {int}, {int})")
    fun p3_point(int1: Int?, int2: Int?, int3: Int?) {
        p3 = point(int1!!, int2!!, int3!!)
    }

    @Then("stripe_at\\(pattern, p1) = white")
    fun stripe_at_pattern_p1_white() {
        assertEquals(white, pattern.at(p1))
    }

    @Then("stripe_at\\(pattern, p2) = white")
    fun stripe_at_pattern_p2_white() {
        assertEquals(white, pattern.at(p2))
    }

    @Then("stripe_at\\(pattern, p3) = white")
    fun stripe_at_pattern_p3_white() {
        assertEquals(white, pattern.at(p3))
    }

    @Given("p2 ← point\\({double}, {double}, {double})")
    fun p2_point(double1: Double?, double2: Double?, double3: Double?) {
        p2 = point(double1!!, double2!!, double3!!)
    }

    lateinit var p4: Tuple
    lateinit var p5: Tuple
    lateinit var p6: Tuple

    @Given("p4 ← point\\({double}, {double}, {double})")
    fun p4_point(double1: Double?, double2: Double?, double3: Double?) {
        p4 = point(double1!!, double2!!, double3!!)
    }

    @Given("p5 ← point\\({int}, {int}, {int})")
    fun p5_point(int1: Int?, int2: Int?, int3: Int?) {
        p5 = point(int1!!, int2!!, int3!!)
    }

    @Given("p6 ← point\\({double}, {double}, {double})")
    fun p6_point(double1: Double?, double2: Double?, double3: Double?) {
        p6 = point(double1!!, double2!!, double3!!)
    }

    @Then("stripe_at\\(pattern, p3) = black")
    fun stripe_at_pattern_p3_black() {
        assertEquals(black, pattern.at(p3))
    }

    @Then("stripe_at\\(pattern, p4) = black")
    fun stripe_at_pattern_p4_black() {
        assertEquals(black, pattern.at(p4))
    }

    @Then("stripe_at\\(pattern, p5) = black")
    fun stripe_at_pattern_p5_black() {
        assertEquals(black, pattern.at(p5))
    }

    @Then("stripe_at\\(pattern, p6) = white")
    fun stripe_at_pattern_p6_white() {
        assertEquals(white, pattern.at(p6))
    }

    lateinit var theObject: Sphere

    @Given("object ← sphere")
    fun object_sphere() {
        theObject = Sphere()
    }

    @Given("set_transform\\(object, transform)")
    fun set_transform_object_transform() {
        theObject.transform = transform
    }

    lateinit var c: Color

    @When("c ← stripe_at_object\\(pattern, object, p1)")
    fun c_stripe_at_object_pattern_object_p1() {
        c = pattern.at(theObject, p1)
    }

    @Then("c = white")
    fun c_white() {
        assertEquals(white, c)
    }

    @Given("set_pattern_transform\\(pattern, transform)")
    fun set_pattern_transform_pattern_transform() {
        pattern.transform = transform
    }

    @Given("set_pattern_transform\\(pattern, t)")
    fun set_pattern_transform_pattern_t() {
        pattern.transform = t
    }

    @Given("t ← translation\\({double}, {double}, {double})")
    fun t_translation(double1: Double?, double2: Double?, double3: Double?) {
        t = translation(double1!!, double2!!, double3!!)
    }

}