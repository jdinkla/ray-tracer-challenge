package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.TestShape
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.Sphere
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class GroupsStepDefinitions {

    private lateinit var g: Group
    private lateinit var g1: Group
    private lateinit var g2: Group
    private lateinit var s1: Sphere
    private lateinit var s2: Sphere
    private lateinit var s3: Sphere

    @Given("g ← group\\()")
    fun g_group() {
        g = Group()
    }

    @Then("g.transform = identity_matrix")
    fun g_transform_identity() {
        assertEquals(identity4, g.transform)
    }

    @Then("g is empty")
    fun g_is_empty() {
        assertTrue(g.isEmpty())
    }

    @Then("g is not empty")
    fun g_is_not_empty() {
        assertFalse(g.isEmpty())
    }

    @Given("s ← test_shape\\()")
    fun s_test_shape() {
        s = TestShape()
    }

    @When("add_child\\(g, s)")
    fun add_child_g_s() {
        g.addChild(s)
    }

    @Then("g includes s")
    fun g_includes_s() {
        assertTrue(s in g.children)
    }

    @Then("s.parent = g")
    fun s_parent_g() {
        assertEquals(g, s.parent)
    }

    @Then("s.parent is nothing")
    fun s_parent_is_nothing() {
        assertNull(s.parent)
    }

    @When("xs ← local_intersect\\(g, r)")
    fun xs_local_intersect_g_r() {
        xs = g.intersectInObjectSpace(r)
    }

    @Given("s1 ← sphere\\()")
    fun s1_sphere() {
        s1 = Sphere()
    }

    @Given("s2 ← sphere\\()")
    fun s2_sphere() {
        s2 = Sphere()
    }

    @Given("s3 ← sphere\\()")
    fun s3_sphere() {
        s3 = Sphere()
    }

    @Given("set_transform\\(s2, translation\\({int}, {int}, {int}))")
    fun set_transform_s2(int1: Int?, int2: Int?, int3: Int?) {
        s2.transform = translation(int1!!, int2!!, int3!!)
    }

    @Given("set_transform\\(s3, translation\\({int}, {int}, {int}))")
    fun set_transform_s3(int1: Int?, int2: Int?, int3: Int?) {
        s3.transform = translation(int1!!, int2!!, int3!!)
    }

    @Given("add_child\\(g, s1)")
    fun add_child_g_s1() {
        g.addChild(s1)
    }

    @Given("add_child\\(g, s2)")
    fun add_child_g_s2() {
        g.addChild(s2)
    }

    @Given("add_child\\(g, s3)")
    fun add_child_g_s3() {
        g.addChild(s3)
    }

    @Then("xs[{int}].object = s1")
    fun xs_object_s1(int1: Int?) {
        assertEquals(s1, xs[int1!!].`object`)
    }

    @Then("xs[{int}].object = s2")
    fun xs_object_s2(int1: Int?) {
        assertEquals(s2, xs[int1!!].`object`)
    }

    @Given("set_transform\\(g, scaling\\({int}, {int}, {int}))")
    fun set_transform_g_scaling(int1: Int?, int2: Int?, int3: Int?) {
        g.transform = scaling(int1!!, int2!!, int3!!)
    }

    @Given("s ← sphere\\()")
    fun s_sphere_parens() {
        s = Sphere()
    }

    @Given("set_transform\\(s, translation\\({int}, {int}, {int}))")
    fun set_transform_s_translation(int1: Int?, int2: Int?, int3: Int?) {
        s.transform = translation(int1!!, int2!!, int3!!)
    }

    @When("xs ← intersect\\(g, r)")
    fun xs_intersect_g_r() {
        xs = g.intersect(r)
    }

    @Given("g1 ← group\\()")
    fun g1_group() {
        g1 = Group()
    }

    @Given("g2 ← group\\()")
    fun g2_group() {
        g2 = Group()
    }

    @Given("set_transform\\(g1, rotation_y\\({double}))")
    fun set_transform_g1_rotation_y(double1: Double?) {
        g1.transform = rotationY(double1!!)
    }

    @Given("set_transform\\(g2, scaling\\({int}, {int}, {int}))")
    fun set_transform_g2_scaling(int1: Int?, int2: Int?, int3: Int?) {
        g2.transform = scaling(int1!!, int2!!, int3!!)
    }

    @Given("add_child\\(g1, g2)")
    fun add_child_g1_g2() {
        g1.addChild(g2)
    }

    @Given("add_child\\(g2, s)")
    fun add_child_g2_s() {
        g2.addChild(s)
    }

    @When("p ← world_to_object\\(s, point\\({int}, {int}, {int}))")
    fun p_world_to_object(int1: Int?, int2: Int?, int3: Int?) {
        p = s.worldToObject(point(int1!!, int2!!, int3!!))
    }

    @Then("p = point\\({int}, {int}, {int})")
    fun p_eq_point(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(point(int1!!, int2!!, int3!!), p)
    }

    @When("n ← normal_to_world\\(s, vector\\({double}, {double}, {double}))")
    fun n_normal_to_world(double1: Double?, double2: Double?, double3: Double?) {
        n = s.normalToWorld(vector(double1!!, double2!!, double3!!))
    }

    @When("n ← normal_at\\(s, point\\({double}, {double}, {double}))")
    fun n_normal_at_inline_point(double1: Double?, double2: Double?, double3: Double?) {
        n = s.normal(point(double1!!, double2!!, double3!!))
    }
}
