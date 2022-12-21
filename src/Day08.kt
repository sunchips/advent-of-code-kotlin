import kotlin.math.max

fun main() {

    fun leftAndTop(input: List<String>): MutableSet<Pair<Int, Int>> {
        val count = mutableSetOf<Pair<Int, Int>>()
        val maxI = input.size
        val maxJ = input[0].length
        val topMin = IntArray(size = maxJ)
        val leftMin = IntArray(size = maxI)
        for (i in input.indices) {
            for (j in input[i].indices) {
                val height: Int = (input[i][j] - '0') + 1
                if (height <= topMin[j] && height <= leftMin[i]) {
                    count.add(Pair(i, j))
                }
                topMin[j] = max(topMin[j], height)
                leftMin[i] = max(leftMin[i], height)
            }
        }
        return count
    }

    fun bottomAndRight(input: List<String>): Set<Pair<Int, Int>> {
        val count = mutableSetOf<Pair<Int, Int>>()
        val maxI = input.size
        val maxJ = input[0].length
        val bottomMin = IntArray(size = maxJ)
        val rightMin = IntArray(size = maxI)
        for (i in input.indices.reversed()) {
            for (j in input[i].indices.reversed()) {
                val height: Int = (input[i][j] - '0') + 1
                if (height <= bottomMin[j] && height <= rightMin[i]) {
                    count.add(Pair(i, j))
                }
                bottomMin[j] = max(bottomMin[j], height)
                rightMin[i] = max(rightMin[i], height)
            }
        }
        return count
    }

    fun part1(input: List<String>): Int {
        val i = input.size
        val j = input[0].length
        val lnt = leftAndTop(input)
        val bnr = bottomAndRight(input)
        val intersect = lnt.intersect(bnr)
        return i * j - intersect.size
    }

    fun calculateScores(input: List<String>): Array<Array<IntArray>> {
        val grid = Array(input.size) { Array(input[0].length) { IntArray(4) } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                val treeHeight = input[i][j] - '0' + 1
                // do left
                if (j == 0) {
                    grid[i][j][0] = 0
                } else {
                    for (k in j - 1 downTo 0) {
                        val tmp = input[i][k] - '0' + 1
                        grid[i][j][0] += 1
                        if (tmp >= treeHeight) {
                            break
                        }
                    }
                }
                // do top
                if (i == 0) {
                    grid[i][j][1] = 0
                } else {
                    for (k in i - 1 downTo 0) {
                        val tmp = input[k][j] - '0' + 1
                        grid[i][j][1] += 1
                        if (tmp >= treeHeight) {
                            break
                        }
                    }
                }
                // do right
                if (j == input[i].length - 1) {
                    grid[i][j][2] = 0
                } else {
                    for (k in j + 1 until input[i].length) {
                        val tmp = input[i][k] - '0' + 1
                        grid[i][j][2] += 1
                        if (tmp >= treeHeight) {
                            break
                        }
                    }
                }
                // do bottom
                if (i == input.size - 1) {
                    grid[i][j][3] = 0
                } else {
                    for (k in i + 1 until input.size) {
                        val tmp = input[k][j] - '0' + 1
                        grid[i][j][3] += 1
                        if (tmp >= treeHeight) {
                            break
                        }
                    }
                }
            }
        }
        return grid
    }

    fun part2(input: List<String>): Int {
        val scores = calculateScores(input)
        var max = 0
        for (i in scores) {
            for (j in i) {
                var beauty = 1
                for (k in j) {
                    beauty *= k
                }
                if (beauty > max) {
                    max = beauty
                }
            }
        }
        return max
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    part1(input).println()
    check(part2(testInput) == 8)
    part2(input).println()
}
