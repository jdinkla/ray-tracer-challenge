package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.Canvas
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
                assertEquals(Color(0), c[x, y])
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

}
