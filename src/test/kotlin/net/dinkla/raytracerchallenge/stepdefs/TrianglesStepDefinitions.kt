package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Triangle
import org.junit.jupiter.api.Assertions.assertEquals

class TrianglesStepDefinitions {

    private lateinit var tri: Triangle
    private lateinit var n1: Vector
    private lateinit var n2: Vector
    private lateinit var n3: Vector

    @Given("t ← triangle\\(p1, p2, p3)")
    fun t_triangle_p1_p2_p3() {
        tri = Triangle(p1, p2, p3)
    }

    @Given("t ← triangle\\(point\\({int}, {int}, {int}), point\\({int}, {int}, {int}), point\\({int}, {int}, {int}))")
    fun t_triangle_inline(
        a1: Int?, a2: Int?, a3: Int?,
        b1: Int?, b2: Int?, b3: Int?,
        c1: Int?, c2: Int?, c3: Int?,
    ) {
        tri = Triangle(point(a1!!, a2!!, a3!!), point(b1!!, b2!!, b3!!), point(c1!!, c2!!, c3!!))
    }

    @Then("t.p1 = p1")
    fun t_p1() {
        assertEquals(p1, tri.p1)
    }

    @Then("t.p2 = p2")
    fun t_p2() {
        assertEquals(p2, tri.p2)
    }

    @Then("t.p3 = p3")
    fun t_p3() {
        assertEquals(p3, tri.p3)
    }

    @Then("t.e1 = vector\\({int}, {int}, {int})")
    fun t_e1(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(vector(int1!!, int2!!, int3!!), tri.e1)
    }

    @Then("t.e2 = vector\\({int}, {int}, {int})")
    fun t_e2(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(vector(int1!!, int2!!, int3!!), tri.e2)
    }

    @Then("t.normal = vector\\({int}, {int}, {int})")
    fun t_normal(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(vector(int1!!, int2!!, int3!!), tri.normal)
    }

    @When("xs ← local_intersect\\(t, r)")
    fun xs_local_intersect_t_r() {
        xs = tri.intersectInObjectSpace(r)
    }

    @When("n1 ← local_normal_at\\(t, point\\({double}, {double}, {double}))")
    fun n1_local_normal_at(double1: Double?, double2: Double?, double3: Double?) {
        n1 = tri.normalInObjectSpace(point(double1!!, double2!!, double3!!))
    }

    @When("n2 ← local_normal_at\\(t, point\\({double}, {double}, {double}))")
    fun n2_local_normal_at(double1: Double?, double2: Double?, double3: Double?) {
        n2 = tri.normalInObjectSpace(point(double1!!, double2!!, double3!!))
    }

    @When("n3 ← local_normal_at\\(t, point\\({double}, {double}, {double}))")
    fun n3_local_normal_at(double1: Double?, double2: Double?, double3: Double?) {
        n3 = tri.normalInObjectSpace(point(double1!!, double2!!, double3!!))
    }

    @Then("n1 = t.normal")
    fun n1_eq_t_normal() {
        assertEquals(tri.normal, n1)
    }

    @Then("n2 = t.normal")
    fun n2_eq_t_normal() {
        assertEquals(tri.normal, n2)
    }

    @Then("n3 = t.normal")
    fun n3_eq_t_normal() {
        assertEquals(tri.normal, n3)
    }
}
