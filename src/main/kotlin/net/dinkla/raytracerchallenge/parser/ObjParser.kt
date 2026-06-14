package net.dinkla.raytracerchallenge.parser

import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Group
import net.dinkla.raytracerchallenge.objects.SmoothTriangle
import net.dinkla.raytracerchallenge.objects.Triangle

// Result of parsing a Wavefront OBJ file. Vertices and normals are 1-indexed to match the file
// format (index 0 is an unused placeholder).
class ObjParser {

    val vertices: MutableList<Point> = mutableListOf(point(0.0, 0.0, 0.0))
    val normals: MutableList<Vector> = mutableListOf(vector(0.0, 0.0, 0.0))
    var ignored: Int = 0
    val defaultGroup: Group = Group()

    private val namedGroups: LinkedHashMap<String, Group> = LinkedHashMap()

    fun addGroup(name: String): Group = Group().also { namedGroups[name] = it }

    fun group(name: String): Group = namedGroups.getValue(name)

    // Combines the default group (if used) and every named group into one group.
    fun objToGroup(): Group = Group().apply {
        if (!defaultGroup.isEmpty()) {
            addChild(defaultGroup)
        }
        namedGroups.values.forEach { addChild(it) }
    }
}

fun parseObjFile(content: String): ObjParser {
    val parser = ObjParser()
    var current = parser.defaultGroup
    for (rawLine in content.lines()) {
        val tokens = rawLine.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        if (tokens.isEmpty()) {
            continue
        }
        when (tokens[0]) {
            "v" -> parser.vertices.add(point(tokens[1].toDouble(), tokens[2].toDouble(), tokens[3].toDouble()))
            "vn" -> parser.normals.add(vector(tokens[1].toDouble(), tokens[2].toDouble(), tokens[3].toDouble()))
            "g" -> current = parser.addGroup(tokens[1])
            "f" -> fanTriangulate(parser, current, tokens.drop(1))
            else -> parser.ignored++
        }
    }
    return parser
}

private fun fanTriangulate(parser: ObjParser, group: Group, specs: List<String>) {
    val vIdx = specs.map { it.split("/")[0].toInt() }
    val nIdx = specs.map {
        val parts = it.split("/")
        if (parts.size >= 3 && parts[2].isNotEmpty()) parts[2].toInt() else null
    }
    for (i in 1 until vIdx.size - 1) {
        val triangle = if (nIdx[0] != null) {
            SmoothTriangle(
                parser.vertices[vIdx[0]], parser.vertices[vIdx[i]], parser.vertices[vIdx[i + 1]],
                parser.normals[nIdx[0]!!], parser.normals[nIdx[i]!!], parser.normals[nIdx[i + 1]!!],
            )
        } else {
            Triangle(parser.vertices[vIdx[0]], parser.vertices[vIdx[i]], parser.vertices[vIdx[i + 1]])
        }
        group.addChild(triangle)
    }
}
