package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.SmoothTriangle
import net.dinkla.raytracerchallenge.objects.Triangle
import net.dinkla.raytracerchallenge.parser.ObjParser
import net.dinkla.raytracerchallenge.parser.parseObjFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class ObjFileStepDefinitions {

    private lateinit var fileContent: String
    private lateinit var parser: ObjParser
    private lateinit var g: Group
    private lateinit var g1: Group
    private lateinit var g2: Group
    private val tris: MutableMap<String, Triangle> = mutableMapOf()

    @Given("{word} ← a file containing:")
    fun a_file_containing(name: String, docString: String) {
        fileContent = docString
    }

    @Given("{word} ← the file {string}")
    fun the_file(name: String, fileName: String) {
        fileContent = requireNotNull(javaClass.getResource("/$fileName")).readText()
    }

    @When("parser ← parse_obj_file\\({word})")
    fun parser_parse_obj_file(name: String) {
        parser = parseObjFile(fileContent)
    }

    @Then("parser should have ignored {int} lines")
    fun parser_ignored(int1: Int?) {
        assertEquals(int1!!, parser.ignored)
    }

    @Then("parser.vertices[{int}] = point\\({double}, {double}, {double})")
    fun parser_vertices(int1: Int?, d1: Double?, d2: Double?, d3: Double?) {
        assertEquals(point(d1!!, d2!!, d3!!), parser.vertices[int1!!])
    }

    @Then("parser.normals[{int}] = vector\\({double}, {double}, {double})")
    fun parser_normals(int1: Int?, d1: Double?, d2: Double?, d3: Double?) {
        assertEquals(vector(d1!!, d2!!, d3!!), parser.normals[int1!!])
    }

    @When("g ← parser.default_group")
    fun g_default_group() {
        g = parser.defaultGroup
    }

    @When("{word} ← first child of g")
    fun first_child_of_g(name: String) {
        tris[name] = g.children[0] as Triangle
    }

    @When("{word} ← second child of g")
    fun second_child_of_g(name: String) {
        tris[name] = g.children[1] as Triangle
    }

    @When("{word} ← third child of g")
    fun third_child_of_g(name: String) {
        tris[name] = g.children[2] as Triangle
    }

    @When("{word} ← first child of g1")
    fun first_child_of_g1(name: String) {
        tris[name] = g1.children[0] as Triangle
    }

    @When("{word} ← first child of g2")
    fun first_child_of_g2(name: String) {
        tris[name] = g2.children[0] as Triangle
    }

    @Then("{word}.p1 = parser.vertices[{int}]")
    fun tri_p1(name: String, int1: Int?) {
        assertEquals(parser.vertices[int1!!], tris.getValue(name).p1)
    }

    @Then("{word}.p2 = parser.vertices[{int}]")
    fun tri_p2(name: String, int1: Int?) {
        assertEquals(parser.vertices[int1!!], tris.getValue(name).p2)
    }

    @Then("{word}.p3 = parser.vertices[{int}]")
    fun tri_p3(name: String, int1: Int?) {
        assertEquals(parser.vertices[int1!!], tris.getValue(name).p3)
    }

    @Then("{word}.n1 = parser.normals[{int}]")
    fun tri_n1(name: String, int1: Int?) {
        assertEquals(parser.normals[int1!!], (tris.getValue(name) as SmoothTriangle).n1)
    }

    @Then("{word}.n2 = parser.normals[{int}]")
    fun tri_n2(name: String, int1: Int?) {
        assertEquals(parser.normals[int1!!], (tris.getValue(name) as SmoothTriangle).n2)
    }

    @Then("{word}.n3 = parser.normals[{int}]")
    fun tri_n3(name: String, int1: Int?) {
        assertEquals(parser.normals[int1!!], (tris.getValue(name) as SmoothTriangle).n3)
    }

    @Then("t2 = t1")
    fun t2_eq_t1() {
        assertEquals(tris.getValue("t1"), tris.getValue("t2"))
    }

    @When("g1 ← {string} from parser")
    fun g1_from_parser(name: String) {
        g1 = parser.group(name)
    }

    @When("g2 ← {string} from parser")
    fun g2_from_parser(name: String) {
        g2 = parser.group(name)
    }

    @When("g ← obj_to_group\\(parser)")
    fun g_obj_to_group() {
        g = parser.objToGroup()
    }

    @Then("g includes {string} from parser")
    fun g_includes(name: String) {
        assertTrue(parser.group(name) in g.children)
    }
}
