import java.util.*
import java.util.regex.Pattern

fun main() {

    fun getStacks(original: MutableList<String>, size: Int): List<Stack<Char>> {
        val reversed = original.reversed()
        val stacks = mutableListOf<Stack<Char>>()
        for (i in 1..size) {
            stacks.add(Stack<Char>())
        }
        for (data in reversed) {
            var start = 0
            for (i in 1..size) {
                val substring = data.substring(start, start + 3)
                val element = substring[1]
                if (element != ' ') {
                    stacks[i - 1].add(element)
                }
                start += 4
            }
        }
        return stacks
    }

    fun processMoves(moves: MutableList<String>, stacks: List<Stack<Char>>) {
        for (move in moves) {
            val groups =
                "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(move)?.groups as MatchNamedGroupCollection
            val howMany: Int = groups[1]!!.value.toInt()
            val stackNumber: Int = groups[2]!!.value.toInt()
            val where: Int = groups[3]!!.value.toInt()
            for (i in 1..howMany) {
                stacks[where - 1].push(stacks[stackNumber - 1].pop())
            }
        }
    }

    fun processMoves9001(moves: MutableList<String>, stacks: List<Stack<Char>>) {
        for (move in moves) {
            val groups =
                "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(move)?.groups as MatchNamedGroupCollection
            val howMany: Int = groups[1]!!.value.toInt()
            val stackNumber: Int = groups[2]!!.value.toInt()
            val where: Int = groups[3]!!.value.toInt()
            val tmp = Stack<Char>()
            for (i in 1..howMany) {
                tmp.push(stacks[stackNumber - 1].pop())
            }
            for (i in 1..howMany) {
                stacks[where - 1].push(tmp.pop())
            }
        }
    }

    fun getTopOfEachStack(stacks: List<Stack<Char>>): String {
        var answer = ""
        for (stack in stacks) {
            answer += stack.peek()
        }
        return answer
    }

    fun part1(input: List<String>): String {
        val original = mutableListOf<String>()
        val moves = mutableListOf<String>()
        for (line in input) {
            if (line == "") {
                continue
            }
            if (line.startsWith("move")) {
                moves.add(line)
            } else {
                original.add(line)
            }
        }
        val lastLine = original.last().trim().split(Pattern.compile("\\s+")).toList()
        original.removeLast()
        val stacks = getStacks(original, lastLine.size)
        processMoves(moves, stacks)
        return getTopOfEachStack(stacks)
    }

    fun part2(input: List<String>): String {
        val original = mutableListOf<String>()
        val moves = mutableListOf<String>()
        for (line in input) {
            if (line == "") {
                continue
            }
            if (line.startsWith("move")) {
                moves.add(line)
            } else {
                original.add(line)
            }
        }
        val lastLine = original.last().trim().split(Pattern.compile("\\s+")).toList()
        original.removeLast()
        val stacks = getStacks(original, lastLine.size)
        processMoves9001(moves, stacks)
        return getTopOfEachStack(stacks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")
    part1(input).println()
    check(part2(testInput) == "MCD")
    part2(input).println()
}
