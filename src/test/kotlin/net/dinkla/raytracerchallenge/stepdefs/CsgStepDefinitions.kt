package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.objects.Csg
import net.dinkla.raytracerchallenge.objects.Cube
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.objects.intersectionAllowed
import org.junit.jupiter.api.Assertions.assertEquals

class CsgStepDefinitions {

    private lateinit var csg: Csg
    private var resultBool: Boolean = false
    private lateinit var resultXs: Intersections

    @Given("s2 ← cube\\()")
    fun s2_cube() {
        namedShapes["s2"] = Cube()
    }

    @When("c ← csg\\({string}, s1, s2)")
    fun c_csg_s1_s2(operation: String) {
        csg = Csg(operation, namedShapes.getValue("s1"), namedShapes.getValue("s2"))
        namedShapes["c"] = csg
    }

    @Given("c ← csg\\({string}, sphere\\(), cube\\())")
    fun c_csg_inline(operation: String) {
        csg = Csg(operation, Sphere(), Cube())
        namedShapes["c"] = csg
    }

    @Then("c.operation = {string}")
    fun c_operation(operation: String) {
        assertEquals(operation, csg.operation)
    }

    @Then("c.left = s1")
    fun c_left_s1() {
        assertEquals(namedShapes.getValue("s1"), csg.left)
    }

    @Then("c.right = s2")
    fun c_right_s2() {
        assertEquals(namedShapes.getValue("s2"), csg.right)
    }

    @Then("s1.parent = c")
    fun s1_parent_c() {
        assertEquals(csg, namedShapes.getValue("s1").parent)
    }

    @Then("s2.parent = c")
    fun s2_parent_c() {
        assertEquals(csg, namedShapes.getValue("s2").parent)
    }

    @When("result ← intersection_allowed\\({string}, {word}, {word}, {word})")
    fun result_intersection_allowed(operation: String, lhit: String, inl: String, inr: String) {
        resultBool = intersectionAllowed(operation, lhit.toBoolean(), inl.toBoolean(), inr.toBoolean())
    }

    @Then("result = {word}")
    fun result_eq(word: String) {
        assertEquals(word.toBoolean(), resultBool)
    }

    @When("result ← filter_intersections\\(c, xs)")
    fun result_filter_intersections() {
        resultXs = csg.filterIntersections(xs)
    }

    @Then("result.count = {int}")
    fun result_count(int1: Int?) {
        assertEquals(int1!!, resultXs.count())
    }

    @Then("result[{int}] = xs[{int}]")
    fun result_index_eq_xs(i: Int?, j: Int?) {
        assertEquals(xs[j!!], resultXs[i!!])
    }
}
