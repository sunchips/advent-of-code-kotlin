fun main() {

    fun sort(pair: Pair<Int, Int>): Pair<Int, Int> {
        return if (pair.first <= pair.second) {
            pair
        } else {
            Pair(pair.second, pair.first)
        }
    }

    fun overlaps(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Boolean {
        var a = sort(pair1)
        var b = sort(pair2)
        if (a.first > b.first) {
            val tmp = a
            a = b
            b = tmp
        } else if (a.first == b.first) {
            if (b.second > a.second) {
                val tmp = a
                a = b
                b = tmp
            }
        }
        return a.second >= b.first
    }

    fun fullyOverlaps(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Boolean {
        var a = sort(pair1)
        var b = sort(pair2)
        if (a.first > b.first) {
            val tmp = a
            a = b
            b = tmp
        } else if (a.first == b.first) {
            if (b.second > a.second) {
                val tmp = a
                a = b
                b = tmp
            }
        }
        return a.first <= b.first && b.second <= a.second
    }

    fun createPair(input: String): Pair<Int, Int> {
        val data = input.split("-")
        return Pair(data[0].toInt(), data[1].toInt())
    }

    fun part1(input: List<String>): Int {
        var count = 0
        for (line in input) {
            val data = line.split(",")
            val pair1 = createPair(data[0])
            val pair2 = createPair(data[1])
            if (overlaps(pair1, pair2) && fullyOverlaps(pair1, pair2)) {
                count += 1
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (line in input) {
            val data = line.split(",")
            val pair1 = createPair(data[0])
            val pair2 = createPair(data[1])
            if (overlaps(pair1, pair2)) {
                count += 1
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    part1(input).println()
    check(part2(testInput) == 4)
    part2(input).println()
}
