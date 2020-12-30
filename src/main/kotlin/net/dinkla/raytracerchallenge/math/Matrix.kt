package net.dinkla.raytracerchallenge.math

import java.util.Objects.hash

class Matrix(val n: Int) {

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
        for (j in 0 until n) {
            for (i in 0 until n) {
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
        throw NotImplementedError()
    }

    companion object {
        fun identity(n: Int): Matrix = Matrix(n).apply {
            loop { i: Int, j: Int ->
                this[i, j] = if (i == j) 1.0 else 0.0
            }
        }

        val identity4 = identity(4)
    }

}