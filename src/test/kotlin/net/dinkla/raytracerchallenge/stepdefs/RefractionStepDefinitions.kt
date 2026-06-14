package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Computations
import net.dinkla.raytracerchallenge.Intersection
import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere
import net.dinkla.raytracerchallenge.schlick
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class RefractionStepDefinitions {

    private var reflectance: Double = 0.0

    @Before
    fun clearNamedShapes() {
        namedShapes.clear()
    }

    private fun resolveShape(name: String): Shape = when (name) {
        "shape" -> shape
        "s" -> s
        else -> namedShapes[name] ?: error("unknown shape: $name")
    }

    @Given("shape ← glass_sphere\\()")
    fun shape_glass_sphere() {
        shape = Sphere.glass()
    }

    @Given("shape ← glass_sphere\\() with:")
    fun shape_glass_sphere_with(dataTable: List<List<String>>) {
        val g = Sphere.glass()
        g.with(dataTable)
        shape = g
    }

    @Given("A ← glass_sphere\\() with:")
    fun a_glass_sphere_with(dataTable: List<List<String>>) {
        val g = Sphere.glass()
        g.with(dataTable)
        namedShapes["A"] = g
    }

    @Given("B ← glass_sphere\\() with:")
    fun b_glass_sphere_with(dataTable: List<List<String>>) {
        val g = Sphere.glass()
        g.with(dataTable)
        namedShapes["B"] = g
    }

    @Given("C ← glass_sphere\\() with:")
    fun c_glass_sphere_with(dataTable: List<List<String>>) {
        val g = Sphere.glass()
        g.with(dataTable)
        namedShapes["C"] = g
    }

    @Given("r ← ray\\(point\\({double}, {double}, {double}), vector\\({double}, {double}, {double}))")
    fun r_ray_inline(d1: Double?, d2: Double?, d3: Double?, d4: Double?, d5: Double?, d6: Double?) {
        r = Ray(point(d1!!, d2!!, d3!!), vector(d4!!, d5!!, d6!!))
    }

    @Given("xs ← intersections\\(i)")
    fun xs_intersections_i() {
        xs = Intersections(i!!)
    }

    // Generic parser for the "t:name" intersection-list syntax, e.g. intersections(2:A, 2.75:B).
    // The colon disambiguates it from the plain intersections(i1, i2) steps.
    @Given("^xs ← intersections\\((.*:.*)\\)$")
    fun xs_intersections_list(list: String) {
        val isecs = list.split(",").map { item ->
            val (t, name) = item.split(":")
            Intersection(t.trim().toDouble(), resolveShape(name.trim()))
        }
        xs = Intersections(*isecs.toTypedArray())
    }

    @When("comps ← prepare_computations\\(i, r, xs)")
    fun comps_prepare_i_r_xs() {
        comps = Computations.prepare(i!!, r, xs)
    }

    @When("comps ← prepare_computations\\(xs[{int}], r, xs)")
    fun comps_prepare_xs_index(int1: Int?) {
        comps = Computations.prepare(xs[int1!!], r, xs)
    }

    @Then("comps.n1 = {double}")
    fun comps_n1(double1: Double?) {
        assertEquals(double1!!, comps.n1, EPSILON)
    }

    @Then("comps.n2 = {double}")
    fun comps_n2(double1: Double?) {
        assertEquals(double1!!, comps.n2, EPSILON)
    }

    @Then("comps.under_point.z > EPSILON\\/{int}")
    fun comps_under_point_z(int1: Int?) {
        assertTrue(comps.underPoint.z > EPSILON / int1!!.toDouble())
    }

    @Then("comps.point.z < comps.under_point.z")
    fun comps_point_z_lt_under_point_z() {
        assertTrue(comps.point.z < comps.underPoint.z)
    }

    @When("reflectance ← schlick\\(comps)")
    fun reflectance_schlick() {
        reflectance = schlick(comps)
    }

    @Then("reflectance = {double}")
    fun reflectance_eq(double1: Double?) {
        assertEquals(double1!!, reflectance, EPSILON)
    }
}
