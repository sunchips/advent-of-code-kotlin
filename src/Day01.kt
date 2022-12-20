fun main() {
    fun part1(input: List<String>): Int {
        var max = Integer.MIN_VALUE
        var tmp = 0
        for (food in input) {
            if (food == "") {
                // next elf
                if (tmp > max) {
                    max = tmp
                }
                tmp = 0
                continue
            }
            tmp += food.toInt()
        }
        if (tmp > max) {
            max = tmp
        }
        return max
    }

    fun part2(input: List<String>): Int {
        val foodCounts = mutableListOf<Int>()
        var tmp = 0
        for (food in input) {
            if (food == "") {
                // next elf
                foodCounts.add(tmp)
                tmp = 0
                continue
            }
            tmp += food.toInt()
        }
        foodCounts.add(tmp)
        foodCounts.sortDescending()
        return foodCounts[0] + foodCounts[1] + foodCounts[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
