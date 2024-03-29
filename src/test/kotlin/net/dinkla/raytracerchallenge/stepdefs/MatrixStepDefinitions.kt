package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import net.dinkla.raytracerchallenge.math.Approx.EPSILON
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Matrix.Companion.identity4
import net.dinkla.raytracerchallenge.math.Tuple
import net.dinkla.raytracerchallenge.math.tuple
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue

class MatrixStepDefinitions {

    lateinit var m: Matrix

    @Given("the following 4x4 matrix M:")
    fun the_following_4x4_matrix_m(dataTable: List<List<Double>>) {
        m = Matrix(4)
        m.from(dataTable)
    }

    @Then("M[{int}, {int}] = {double}")
    fun m(int1: Int?, int2: Int?, double1: Double?) {
        assertEquals(double1!!, m[int1!!, int2!!], EPSILON)
    }

    @Given("the following 2x2 matrix M:")
    fun the_following_2x2_matrix_m(dataTable: List<List<Double>>) {
        m = Matrix(2)
        m.from(dataTable)
    }

    @Given("the following 3x3 matrix M:")
    fun the_following_3x3_matrix_m(dataTable: List<List<Double>>) {
        m = Matrix(3)
        m.from(dataTable)
    }

    lateinit var a: Matrix

    @Given("the following matrix A:")
    fun the_following_matrix_a(dataTable: List<List<Double>>) {
        a = Matrix(4)
        a.from(dataTable)
    }

    lateinit var b: Matrix

    @Given("the following matrix B:")
    fun the_following_matrix_b(dataTable: List<List<Double>>) {
        b = Matrix(4)
        b.from(dataTable)
    }

    @Then("A = B")
    fun `a=b`() {
        assertEquals(a, b)
    }

    @Then("A != B")
    fun a_b() {
        assertNotEquals(a, b)
    }

    @Then("A * B is the following 4x4 matrix:")
    fun a_b_is_the_following_4x4_matrix(dataTable: List<List<Double>>) {
        val aTimesB = Matrix(4)
        aTimesB.from(dataTable)

        assertEquals(aTimesB, a * b)
    }

    lateinit var b_tuple: Tuple

    @Given("b ← tuple\\({int}, {int}, {int}, {int})")
    fun b_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        b_tuple = tuple(int1!!, int2!!, int3!!, int4!!)
    }

    @Then("A * b = tuple\\({int}, {int}, {int}, {int})")
    fun a_b_tuple(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        val result = tuple(int1!!, int2!!, int3!!, int4!!)
        assertEquals(result, a * b_tuple)
    }

    @Then("A * identity_matrix = A")
    fun a_identity_matrix_a() {
        assertEquals(a, a * identity4)
    }

    @Then("identity_matrix * a = a")
    fun identity_matrix_a_a() {
        assertEquals(a_tuple, identity4 * a_tuple)
    }

    @Then("transpose\\(A) is the following matrix:")
    fun transpose_a_is_the_following_matrix(dataTable: List<List<Double>>) {
        val transpose_a = Matrix(4)
        transpose_a.from(dataTable)

        assertEquals(transpose_a, a.transpose())
    }

    @Given("A ← transpose\\(identity_matrix)")
    fun a_transpose_identity_matrix() {
        a = identity4.transpose()
    }

    @Then("A = identity_matrix")
    fun a_identity_matrix() {
        assertEquals(identity4, a)
    }

    @Given("the following 2x2 matrix A:")
    fun the_following_2x2_matrix_a(dataTable: List<List<Double>>) {
        a = Matrix(2)
        a.from(dataTable)
    }

    @Then("determinant\\(A) = {int}")
    fun determinant_a(int1: Int?) {
        assertEquals(int1!!.toDouble(), a.determinant(), EPSILON)
    }

    @Given("the following 3x3 matrix A:")
    fun the_following_3x3_matrix_a(dataTable: List<List<Double>>) {
        a = Matrix(3)
        a.from(dataTable)
    }

    @Then("submatrix\\(A, {int}, {int}) is the following 2x2 matrix:")
    fun submatrix_a_is_the_following_2x2_matrix(int1: Int?, int2: Int?, dataTable: List<List<Double>>) {
        val sub_a = Matrix(2)
        sub_a.from(dataTable)
        assertEquals(sub_a, a.subMatrix(int1!!, int2!!))
    }

    @Given("the following 4x4 matrix A:")
    fun the_following_4x4_matrix_a(dataTable: List<List<Double>>) {
        a = Matrix(4)
        a.from(dataTable)
    }

    @Then("submatrix\\(A, {int}, {int}) is the following 3x3 matrix:")
    fun submatrix_a_is_the_following_3x3_matrix(int1: Int?, int2: Int?, dataTable: List<List<Double>>) {
        val sub_a = Matrix(3)
        sub_a.from(dataTable)
        assertEquals(sub_a, a.subMatrix(int1!!, int2!!))
    }

    @Given("B ← submatrix\\(A, {int}, {int})")
    fun b_submatrix_a(int1: Int?, int2: Int?) {
        b = a.subMatrix(int1!!, int2!!)
    }

    @Then("determinant\\(B) = {int}")
    fun determinant_b(int1: Int?) {
        assertEquals(int1!!.toDouble(), b.determinant(), EPSILON)
    }

    @Then("minor\\(A, {int}, {int}) = {int}")
    fun minor_a(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(int3!!.toDouble(), a.minor(int1!!, int2!!), EPSILON)
    }

    @Then("cofactor\\(A, {int}, {int}) = {int}")
    fun cofactor_a(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(int3!!.toDouble(), a.cofactor(int1!!, int2!!), EPSILON)
    }

    @Then("A is invertible")
    fun a_is_invertible() {
       assertTrue(a.isInvertible())
    }

    @Then("A is not invertible")
    fun a_is_not_invertible() {
        assertFalse(a.isInvertible())
    }

    @Given("B ← inverse\\(A)")
    fun b_inverse_a() {
        b = a.inverse()
    }

    @Then("B[{int}, {int}] = {int}\\/{int}")
    fun b(int1: Int?, int2: Int?, int3: Int?, int4: Int?) {
        assertEquals(int3!!.toDouble()/int4!!.toDouble(), b[int1!!, int2!!], EPSILON)
    }

    @Then("B is the following 4x4 matrix:")
    fun b_is_the_following_4x4_matrix(dataTable: List<List<Double>>) {
        val b_expected = Matrix(4)
        b_expected.from(dataTable)
        assertEquals(b_expected, b)
    }

    @Then("inverse\\(A) is the following 4x4 matrix:")
    fun inverse_a_is_the_following_4x4_matrix(dataTable: List<List<Double>>) {
        val inverse_a = Matrix(4)
        inverse_a.from(dataTable)
        assertEquals(inverse_a, a.inverse())
    }

    @Given("the following 4x4 matrix B:")
    fun the_following_4x4_matrix_b(dataTable: List<List<Double>>) {
        b = Matrix(4)
        b.from(dataTable)
    }

    lateinit var c : Matrix

    @Given("C ← A * B")
    fun c_a_b() {
        c = a * b
    }

    @Then("C * inverse\\(B) = A")
    fun c_inverse_b_a() {
        assertEquals(a, c * b.inverse())
    }

}
