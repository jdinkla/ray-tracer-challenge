package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point

class TestPattern : Pattern() {
    override fun atInPatternSpace(point: Point): Color = Color(point.x, point.y, point.z)
}