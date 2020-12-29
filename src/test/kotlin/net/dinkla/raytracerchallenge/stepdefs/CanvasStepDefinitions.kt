package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Canvas
import net.dinkla.raytracerchallenge.PPM
import net.dinkla.raytracerchallenge.math.Color
import kotlin.test.assertEquals

class CanvasStepDefinitions {

    lateinit var c: Canvas

    @Given("c ← canvas\\({int}, {int})")
    fun c_canvas(int1: Int?, int2: Int?) {
        c = Canvas(int1!!, int2!!)
    }

    @Then("c.width = {int}")
    fun c_width(int1: Int?) {
        assertEquals(c.width, int1!!)
    }

    @Then("c.height = {int}")
    fun c_height(int1: Int?) {
        assertEquals(c.height, int1!!)
    }

    @Then("every pixel of c is color\\({int}, {int}, {int})")
    fun every_pixel_of_c_is_color(int1: Int?, int2: Int?, int3: Int?) {
        for (y in 0 until c.height) {
            for (x in 0 until c.width) {
                assertEquals(Color(int1!!, int2!!, int3!!), c[x, y])
            }
        }
    }

    lateinit var red: Color

    @Given("red ← color\\({int}, {int}, {int})")
    fun red_color(int1: Int?, int2: Int?, int3: Int?) {
        red = Color(int1!!, int2!!, int3!!)
    }

    @When("write_pixel\\(c, {int}, {int}, red)")
    fun write_pixel_c_red(int1: Int?, int2: Int?) {
        c[int1!!, int2!!] = red
    }

    @Then("pixel_at\\(c, {int}, {int}) = red")
    fun pixel_at_c_red(int1: Int?, int2: Int?) {
        assertEquals(red, c[int1!!, int2!!])
    }

    lateinit var ppm: PPM

    @When("ppm ← canvas_to_ppm\\(c)")
    fun ppm_canvas_to_ppm_c() {
        ppm = PPM.create(c)
    }

    @Then("lines {int}-{int} of ppm are")
    fun lines_of_ppm_are(int1: Int?, int2: Int?, docString: String?) {
        val s = ppm.contents.split("\n").slice((int1!!-1) until int2!!).joinToString("\n")
        assertEquals(docString!!, s)
    }

    @When("write_pixel\\(c, {int}, {int}, c1)")
    fun write_pixel_c_c1(int1: Int?, int2: Int?) {
        c[int1!!, int2!!] = c1
    }

    @When("write_pixel\\(c, {int}, {int}, c2)")
    fun write_pixel_c_c2(int1: Int?, int2: Int?) {
        c[int1!!, int2!!] = c2
    }

    @When("write_pixel\\(c, {int}, {int}, c3)")
    fun write_pixel_c_c3(int1: Int?, int2: Int?) {
        c[int1!!, int2!!] = c3
    }

    @When("every pixel of c is set to color\\({double}, {double}, {double})")
    fun every_pixel_of_c_is_set_to_color(double1: Double?, double2: Double?, double3: Double?) {
        val color = Color(double1!!, double2!!, double3!!)
        for (y in 0 until c.height) {
            for (x in 0 until c.width) {
                c[x, y] = color
            }
        }
    }

    @Then("ppm ends with a newline character")
    fun ppm_ends_with_a_newline_character() {
        assertEquals('\n', ppm.contents.last())
    }
}
