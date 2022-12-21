fun main() {

    fun getStartOfPacketMarker(data: CharArray, numberOfUniqueChars: Int): Int {
        val map = mutableMapOf<Char, Int>()
        for (i in 1..data.size) {
            val c = data[i - 1]
            if (map[c] == null) {
                map[c] = i
            } else {
                val target = map[c]!!
                val currentKeys = map.keys.toList()
                for (key in currentKeys) {
                    if (map[key]!! <= target) {
                        map.remove(key)
                    }
                }
                map[c] = i
            }
            if (map.size == numberOfUniqueChars) {
                return i
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val data = input[0].toCharArray()
        return getStartOfPacketMarker(data, 4)
    }

    fun part2(input: List<String>): Int {
        val data = input[0].toCharArray()
        return getStartOfPacketMarker(data, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11)

    val input = readInput("Day06")
    part1(input).println()
    check(part2(testInput) == 26)
    part2(input).println()
}
