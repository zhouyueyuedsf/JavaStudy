package operator

data class Point(val x: Int, val y: Int)

operator fun Point.invoke() {

}

fun main() {
    val point = Point(1, 2)
    point()
}