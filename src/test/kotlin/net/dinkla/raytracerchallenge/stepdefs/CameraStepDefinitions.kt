package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Camera
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Transformation.rotationY
import net.dinkla.raytracerchallenge.math.Transformation.translation
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.viewTransform
import org.junit.jupiter.api.Assertions.assertEquals

class CameraStepDefinitions {

    var hsize: Int = 0

    @Given("hsize ← {int}")
    fun hsize(int1: Int?) {
        hsize = int1!!
    }

    var vsize: Int = 0

    @Given("vsize ← {int}")
    fun vsize(int1: Int?) {
        vsize = int1!!
    }

    var fieldOfView: Double = 0.0

    @Given("field_of_view ← π\\/{int}")
    fun field_of_view_π(int1: Int?) {
        fieldOfView = Math.PI / int1!!.toDouble()
    }

    lateinit var c: Camera

    @When("c ← camera\\(hsize, vsize, field_of_view)")
    fun c_camera_hsize_vsize_field_of_view() {
        c = Camera(hsize, vsize, fieldOfView)
    }

    @Then("c.hsize = {int}")
    fun c_hsize(int1: Int?) {
        assertEquals(int1!!, c.hSize)
    }

    @Then("c.vsize = {int}")
    fun c_vsize(int1: Int?) {
        assertEquals(int1!!, c.vSize)
    }

    @Then("c.field_of_view = π\\/{int}")
    fun c_field_of_view_π(int1: Int?) {
        assertEquals(Math.PI / int1!!, c.fieldOfView, EPSILON)
    }

    @Then("c.transform = identity_matrix")
    fun c_transform_identity_matrix() {
       assertEquals(identity4, c.transform)
    }

    @Given("c ← camera\\({int}, {int}, {double})")
    fun c_camera(int1: Int?, int2: Int?, double1: Double?) {
        c = Camera(int1!!, int2!!, double1!!)
    }

    @Then("c.pixel_size = {double}")
    fun c_pixel_size(double1: Double?) {
        assertEquals(double1!!, c.pixelSize, EPSILON)
    }

    @Given("field_of_view ← {double}")
    fun field_of_view(double1: Double?) {
        fieldOfView = double1!!
    }

    @Then("c.field_of_view = {double}")
    fun c_field_of_view(double1: Double?) {
        assertEquals(double1!!, c.fieldOfView, EPSILON)
    }

    @When("r ← ray_for_pixel\\(c, {int}, {int})")
    fun r_ray_for_pixel_c(int1: Int?, int2: Int?) {
        r = c.rayForPixel(int1!!, int2!!)
    }

    @Then("r.origin = point\\({int}, {int}, {int})")
    fun r_origin_point(int1: Int?, int2: Int?, int3: Int?) {
        val expected = point(int1!!, int2!!, int3!!)
        assertEquals(expected, r.origin)
    }

    @Then("r.direction = vector\\({double}, {double}, {double})")
    fun r_direction_vector(double1: Double?, double2: Double?, double3: Double?) {
        val expected = vector(double1!!, double2!!, double3!!)
        assertEquals(expected, r.direction)
    }

    @When("c.transform ← rotation_y\\({double}) * translation\\({int}, {int}, {int})")
    fun c_transform_rotation_y_translation(double1: Double?, int1: Int?, int2: Int?, int3: Int?) {
        c.transform = rotationY(double1!!) * translation(int1!!, int2!!, int3!!)
    }

    lateinit var image: Canvas

    @When("image ← render\\(c, w)")
    fun image_render_c_w() {
        image = w.render(c)
    }

    @Then("pixel_at\\(image, {int}, {int}) = color\\({double}, {double}, {double})")
    fun pixel_at_image_color(int1: Int?, int2: Int?, double1: Double?, double2: Double?, double3: Double?) {
        val expected  = Color(double1!!, double2!!, double3!!)
        assertEquals(expected, image[int1!!, int2!!])
    }

    @Given("c.transform ← view_transform\\(from, to, up)")
    fun c_transform_view_transform_from_to_up() {
        c.transform = viewTransform(from, to, up)
    }

}


