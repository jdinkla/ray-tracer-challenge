package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.patterns.Pattern

class TestPattern : Pattern() {
    override fun atInPatternSpace(point: Point): Color = Color(point.x, point.y, point.z)
}