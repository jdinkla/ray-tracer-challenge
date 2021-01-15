package net.dinkla.raytracerchallenge.stepdefs

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.*
import net.dinkla.raytracerchallenge.objects.Shape

lateinit var c: Color
lateinit var c1: Color
lateinit var c2: Color
lateinit var c3: Color
lateinit var c4: Color
lateinit var intensity: Color
lateinit var black: Color
lateinit var white: Color

lateinit var a_tuple: Tuple
lateinit var p1: Tuple
lateinit var p2: Tuple
lateinit var p3: Tuple

lateinit var p: Point
lateinit var position: Point
lateinit var from: Point
lateinit var to: Point
lateinit var origin: Point

lateinit var direction: Vector
lateinit var v: Vector
lateinit var up: Vector
lateinit var n: Vector

lateinit var r: Ray

lateinit var s: Shape

lateinit var xs: Intersections

var i: Intersection? = null

lateinit var transform : Matrix
lateinit var t: Matrix

lateinit var m: Material

lateinit var light: PointLight

lateinit var comps: Computations

lateinit var shape: Shape

lateinit var w: World
