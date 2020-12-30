package net.dinkla.raytracerchallenge.math

import java.util.Objects.hash

class Matrix(val n: Int) {

    init {
        assert(n >= 2)
    }

    private var m: DoubleArray = DoubleArray(n * n)

    operator fun get(i: Int, j: Int) = m[index(i, j)]

    operator fun set(i: Int, j: Int, value: Double) {
        m[index(i, j)] = value
    }

    operator fun set(i: Int, value: Double) {
        m[i] = value
    }

    operator fun times(matrix: Matrix): Matrix {
        val result = Matrix(n)
        loop { i: Int, j: Int ->
            var sum = 0.0
            for (k in 0..3) {
                sum += m[index(i, k)] * matrix.m[index(k, j)]
            }
            result.m[index(i, j)] = sum
        }
        return result
    }

    operator fun times(p: Tuple): Tuple {
        assert(n == 4)
        fun add(i: Int): Double {
            val idx = index(i, 0)
            return m[idx] * p.x + m[idx + 1] * p.y + m[idx + 2] * p.z + m[idx + 3] * p.w
        }
        return Tuple(add(0), add(1), add(2), add(3))
    }

    fun transpose(): Matrix {
        val result = Matrix(n)
        loop { i: Int, j: Int ->
            result[j, i] = m[index(i, j)]
        }
        return result
    }

    fun index(i: Int, j: Int) = n * i + j

    inline fun loop(f: (Int, Int) -> Unit) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                f(i, j)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (null == other || other !is Matrix) {
            return false
        } else {
            loop { i: Int, j: Int ->
                if (this[i, j] != other[i, j]) {
                    return false
                }
            }
            return true
        }
    }

    override fun hashCode(): Int = hash(m)

    fun from(dataTable: List<List<Double>>) {
        assert(dataTable.size <= n * n)
        var idx = 0
        for (j in dataTable) {
            for (i in j) {
                m[idx++] = i
            }
        }
    }

    fun determinant(): Double {
        if (n == 2) {
            return m[0] * m[3] - m[1] * m[2]
        }
        var det = 0.0
        for (i in 0 until n) {
            det += m[i] * cofactor(0, i)
        }
        return det
    }

    fun inverse(): Matrix {
        return Matrix(n)
    }

    fun submatrix(i: Int, j: Int): Matrix {
        assert(n >= 2)
        val result = Matrix(n-1)
        var idx = 0
        loop { li: Int, lj: Int ->
            if (li != i) {
                if (lj != j) {
                    result[idx++] = m[index(li, lj)]
                }
            }
        }
        return result
    }

    fun minor(i: Int, j: Int): Double = submatrix(i, j).determinant()

    fun cofactor(i: Int, j: Int): Double {
        val m = minor(i, j)
        return if (i+j % 2 == 0) m else -m
    }

    override fun toString() = buildString {
        fun line(l: Int): String {
            var sep = ""
            return buildString {
                for (i in 0 until n) {
                    append("${m[index(i, l)]} ")
                }
            }
        }
        for (j in 0 until n) {
            appendLine(line(j))
        }
    }

    fun isInvertible(): Boolean = determinant() != 0.0

    companion object {
        fun identity(n: Int): Matrix = Matrix(n).apply {
            loop { i: Int, j: Int ->
                this[i, j] = if (i == j) 1.0 else 0.0
            }
        }

        val identity4 = identity(4)
    }

}