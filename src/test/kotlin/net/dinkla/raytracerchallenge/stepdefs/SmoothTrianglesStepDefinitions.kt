package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.SmoothTriangle
import org.junit.jupiter.api.Assertions.assertEquals

class SmoothTrianglesStepDefinitions {

    private lateinit var tri: SmoothTriangle
    private lateinit var sn1: Vector
    private lateinit var sn2: Vector
    private lateinit var sn3: Vector

    @Given("n1 ← vector\\({int}, {int}, {int})")
    fun n1_vector(int1: Int?, int2: Int?, int3: Int?) {
        sn1 = vector(int1!!, int2!!, int3!!)
    }

    @Given("n2 ← vector\\({int}, {int}, {int})")
    fun n2_vector(int1: Int?, int2: Int?, int3: Int?) {
        sn2 = vector(int1!!, int2!!, int3!!)
    }

    @Given("n3 ← vector\\({int}, {int}, {int})")
    fun n3_vector(int1: Int?, int2: Int?, int3: Int?) {
        sn3 = vector(int1!!, int2!!, int3!!)
    }

    @When("tri ← smooth_triangle\\(p1, p2, p3, n1, n2, n3)")
    fun tri_smooth_triangle() {
        tri = SmoothTriangle(p1, p2, p3, sn1, sn2, sn3)
    }

    @Then("tri.p1 = p1")
    fun tri_p1() = assertEquals(p1, tri.p1)

    @Then("tri.p2 = p2")
    fun tri_p2() = assertEquals(p2, tri.p2)

    @Then("tri.p3 = p3")
    fun tri_p3() = assertEquals(p3, tri.p3)

    @Then("tri.n1 = n1")
    fun tri_n1() = assertEquals(sn1, tri.n1)

    @Then("tri.n2 = n2")
    fun tri_n2() = assertEquals(sn2, tri.n2)

    @Then("tri.n3 = n3")
    fun tri_n3() = assertEquals(sn3, tri.n3)

    @When("xs ← local_intersect\\(tri, r)")
    fun xs_local_intersect_tri_r() {
        xs = tri.intersectInObjectSpace(r)
    }

    @Then("xs[{int}].u = {double}")
    fun xs_u(int1: Int?, double1: Double?) {
        assertEquals(double1!!, xs[int1!!].u, EPSILON)
    }

    @Then("xs[{int}].v = {double}")
    fun xs_v(int1: Int?, double1: Double?) {
        assertEquals(double1!!, xs[int1!!].v, EPSILON)
    }

    @When("i ← intersection_with_uv\\({double}, tri, {double}, {double})")
    fun i_intersection_with_uv_tri(t: Double?, u: Double?, v: Double?) {
        i = Intersection(t!!, tri, u!!, v!!)
    }

    @When("n ← normal_at\\(tri, point\\({int}, {int}, {int}), i)")
    fun n_normal_at_tri(int1: Int?, int2: Int?, int3: Int?) {
        n = tri.normal(point(int1!!, int2!!, int3!!), i)
    }
}
