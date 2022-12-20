import kotlin.streams.toList

fun main() {

    fun findFirstCommon(s1: String, s2: String): Char {
        val chars1 = s1.chars()
        val chars2 = s2.chars()
        val intersect = chars1.toList().intersect(chars2.toList().toSet()).toIntArray()
        return intersect[0].toChar()
    }

    fun findCommon(s1: String, s2: String): String {
        val chars1 = s1.chars()
        val chars2 = s2.chars()
        val intersect = chars1.toList().intersect(chars2.toList().toSet()).map { input -> input.toChar() }.toCharArray()
        return String(intersect)
    }

    fun getVal(common: Char): Int {
        return if (common.isUpperCase()) {
            common - 'A' + 27
        } else {
            common - 'a' + 1
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val chunked = line.chunked(line.length / 2)
            //println(chunked[0] + " " + chunked[1])
            val common = findFirstCommon(chunked[0], chunked[1])
            //println(common)
            val priority = getVal(common)
            //println(priority)
            //println()
            sum += priority
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val chunked = input.chunked(3)
        for (group in chunked) {
            val firstTwo = findCommon(group[0], group[1])
            //println(firstTwo)
            val nextTwo = findCommon(firstTwo, group[2])
            //println(nextTwo)
            val priority = getVal(nextTwo[0])
            //println(priority)
            //println()
            sum += priority
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    part1(input).println()
    check(part2(testInput) == 70)
    part2(input).println()
}
