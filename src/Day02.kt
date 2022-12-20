fun main() {
    fun fight(opponent: String, you: String): Int {
        var score = 0
        when (opponent) {
            "A" -> { // rock
                when (you) {
                    "X" -> { // rock
                        score += 1
                        // draw
                        score += 3
                    }

                    "Y" -> { // paper
                        score += 2
                        score += 6
                    }

                    else -> { // scissors
                        score += 3
                        score += 0
                    }
                }
            }

            "B" -> {
                when (you) {
                    "X" -> { // rock
                        score += 1
                        score += 0
                    }

                    "Y" -> { // paper
                        score += 2
                        score += 3
                    }

                    else -> { // scissors
                        score += 3
                        score += 6
                    }
                }
            }

            else -> { // scissors
                when (you) {
                    "X" -> { // rock
                        score += 1
                        score += 6
                    }

                    "Y" -> { // paper
                        score += 2
                        score += 0
                    }

                    else -> { // scissors
                        score += 3
                        score += 3
                    }
                }
            }
        }
        return score
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val strategy = line.split(" ")
            val score = fight(strategy[0], strategy[1])
            sum += score
        }
        return sum
    }

    fun getChoice(opponent: String, strategy: String): String {
        when (opponent) {
            "A" -> { // rock
                return when (strategy) {
                    "X" -> { // lose
                        "Z" // scissors
                    }

                    "Y" -> { // draw
                        "X" // rock
                    }

                    else -> { // win
                        "Y" // paper
                    }
                }
            }

            "B" -> { // paper
                return when (strategy) {
                    "X" -> { // lose
                        "X" // rock
                    }

                    "Y" -> { // draw
                        "Y" // paper
                    }

                    else -> { // win
                        "Z" // scissors
                    }
                }
            }

            else -> { // scissors
                return when (strategy) {
                    "X" -> { // lose
                        "Y" // paper
                    }

                    "Y" -> { // draw
                        "Z" // scissors
                    }

                    else -> { // win
                        "X" // rock
                    }
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val strategy = line.split(" ")
            val choice = getChoice(strategy[0], strategy[1])
            // println(strategy + " => we must pick " + choice)
            val score = fight(strategy[0], choice)
            sum += score
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    check(part2(testInput) == 12)
    part1(input).println()
    part2(input).println()
}
