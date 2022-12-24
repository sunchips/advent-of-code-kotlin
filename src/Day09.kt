import kotlin.math.abs

fun main() {

    class Point(
        var y: Long, var x: Long
    ) {
        override fun toString(): String {
            return "Point(y=$y, x=$x)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Point

            if (y != other.y) return false
            if (x != other.x) return false

            return true
        }

        override fun hashCode(): Int {
            var result = y.hashCode()
            result = 31 * result + x.hashCode()
            return result
        }

        fun copy(): Point {
            return Point(y, x)
        }

    }

    fun isCloseEnough(head: Point, tail: Point): Boolean {
        return abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1
    }

    fun part1(input: List<String>): Int {
        val seen = mutableSetOf<Point>()
        val head = Point(0, 0)
        val tail = Point(0, 0)
        seen.add(tail.copy())
        for (line in input) {
            val action = line.split("\\s+".toRegex())
            val dir = action[0]
            val count = action[1].toInt()
            if (dir == "R") {
                for (i in 1..count) {
                    head.x += 1
                    // T..
                    // T.H
                    // T..
                    if (!isCloseEnough(head, tail)) {
                        if (tail.y > head.y) {
                            tail.y -= 1
                        } else if (tail.y < head.y) {
                            tail.y += 1
                        }
                        tail.x += 1
                    }
                    seen.add(tail.copy())
                }
            } else if (dir == "L") {
                for (i in 1..count) {
                    head.x -= 1
                    // ..T
                    // H.T
                    // ..T
                    if (!isCloseEnough(head, tail)) {
                        if (tail.y > head.y) {
                            tail.y -= 1
                        } else if (tail.y < head.y) {
                            tail.y += 1
                        }
                        tail.x -= 1
                    }
                    seen.add(tail.copy())
                }
            } else if (dir == "U") {
                for (i in 1..count) {
                    head.y += 1
                    // .H.
                    // ...
                    // TTT
                    if (!isCloseEnough(head, tail)) {
                        if (tail.x > head.x) {
                            tail.x -= 1
                        } else if (tail.x < head.x) {
                            tail.x += 1
                        }
                        tail.y += 1
                    }
                    seen.add(tail.copy())
                }
            } else {
                for (i in 1..count) {
                    head.y -= 1
                    // TTT
                    // ...
                    // .H.
                    if (!isCloseEnough(head, tail)) {
                        if (tail.x > head.x) {
                            tail.x -= 1
                        } else if (tail.x < head.x) {
                            tail.x += 1
                        }
                        tail.y -= 1
                    }
                    seen.add(tail.copy())
                }
            }
        }
        return seen.size
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val task = "Day09"

    // test if implementation meets criteria from the description, like:
    val input = readInput(task)
    val testInput = readInput(task + "_test")

    check(part1(testInput) == 13)
    part1(input).println()

    check(part2(testInput) == 0)
    part2(input).println()
}
