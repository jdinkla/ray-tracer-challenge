package net.dinkla.raytracerchallenge.stepdefs

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Matrix
import net.dinkla.raytracerchallenge.math.Transformation
import net.dinkla.raytracerchallenge.math.Tuple
import net.dinkla.raytracerchallenge.objects.Shape


private fun tupleWith(s: String): Tuple {
    val parts = s.substring(1, s.length-1).split(",")
    val doubles = parts.map { it.toDouble() }
    assert(doubles.size == 3)
    return Tuple(doubles[0], doubles[1], doubles[2], 0.0)
}

private fun colorWith(s: String): Color {
    val t = tupleWith(s)
    return Color(t.x, t.y, t.z)
}

private fun transformWith(s: String): Matrix {
    val parts = s.split("(")
    return when (parts[0]) {
        "scaling" -> {
            val t = tupleWith("(" + parts[1])
            Transformation.scaling(t.x, t.y, t.z)
        }
        "translation" -> {
            val t = tupleWith("(" + parts[1])
            Transformation.translation(t.x, t.y, t.z)
        }
        else -> throw IllegalArgumentException()
    }
}

fun Shape.with(dataTable: List<List<String>>) {
    for (line in dataTable) {
        when (line[0]) {
            "material.color" -> material.color = colorWith(line[1])
            "material.diffuse" -> material.diffuse = line[1].toDouble()
            "material.specular" -> material.specular = line[1].toDouble()
            "material.reflective" -> material.reflective = line[1].toDouble()
            "transform" -> transform = transformWith(line[1])
            else -> throw IllegalArgumentException()
        }
    }
}
