package cz.jaro.dopravnipodniky.sketches

import cz.jaro.dopravnipodniky.pocatecniPosunutiX
import cz.jaro.dopravnipodniky.pocatecniPosunutiY
import processing.core.PApplet

var s: Float = 0F

var tx: Float = pocatecniPosunutiX // v px
var ty: Float = pocatecniPosunutiY // v px

var x1: Float = -1F
var y1: Float = -1F

fun Sketch.zachovavatPodobnost() {

    val (klik1, klik2) = touches.toList().subList(0, 2)

    val (x1, y1) = listOf(klik1.x, klik1.y)
    val (x2, y2) = listOf(klik2.x, klik2.y)

    val d = PApplet.dist(x1, y1, x2, y2)

    if (s == 0F) s = d

    velikostBloku *= d / s

    s = d
}

fun Sketch.sunout() {

    val x2 = touches.first().x
    val y2 = touches.first().y

    if (x1 == -1F || y1 == -1F) { x1 = x2; y1 = y2 }

    val deltaX = x2 - x1
    val deltaY = y2 - y1

    tx += deltaX

    ty += deltaY

    x1 = x2
    y1 = y2

}