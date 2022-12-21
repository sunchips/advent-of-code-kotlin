import java.util.regex.Pattern

fun main() {

    data class File(
        val name: String, val size: Long
    ) {
        override fun toString(): String {
            return "File(name='$name', size=$size)"
        }
    }

    class Directory(
        val name: String,
        var size: Long = 0,
        var parent: Directory? = null,
        val children: MutableList<Directory> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) {
        fun addFile(f: File) {
            // check(!files.contains(f))
            val foundFile = files.firstOrNull { file: File -> file.name == f.name }
            if (foundFile == null) {
                files.add(f)
                var currentDirectory: Directory = this
                size += f.size
                while (currentDirectory.parent != null) {
                    currentDirectory = currentDirectory.parent!!
                    currentDirectory.size += f.size
                }
            }
        }

        fun addDirectory(d: Directory) {
            // check(!children.contains(d))
            val existingChild = children.firstOrNull { directory: Directory -> directory.name == d.name }
            if (existingChild == null) {
                d.parent = this
                children.add(d)
                size += d.size
            }
        }

        override fun toString(): String {
            return "Directory(name='$name', size=$size, parent=${parent?.name}, children=$children, files=$files)"
        }
    }

    fun processCDCommand(command: String, root: Directory): Directory {
        var currentDirectory = root
        val action = command.substring(2).split(" ")
        when (val path = action[1]) {
            "/" -> {
                while (currentDirectory.name != "/") {
                    currentDirectory = currentDirectory.parent!!
                }
                return currentDirectory
            }

            ".." -> {
                return if (currentDirectory.name != "/") {
                    currentDirectory.parent!!
                } else {
                    currentDirectory
                }
            }

            else -> {
                val foundDirectory =
                    currentDirectory.children.firstOrNull { directory: Directory -> directory.name == path }
                return if (foundDirectory == null) {
                    val newDirectory = Directory(path)
                    currentDirectory.addDirectory(newDirectory)
                    newDirectory
                } else {
                    foundDirectory
                }
            }
        }
    }

    fun isCommand(line: String): Boolean {
        return line.startsWith("$")
    }

    fun processLSCommand(dirData: MutableList<String>, currentDirectory: Directory): Directory {
        for (data in dirData) {
            val split = data.split(Pattern.compile("\\s+"))
            if (split[0] == "dir") {
                val dirName = split[1]
                currentDirectory.addDirectory(Directory(dirName))
            } else {
                val fileLength = split[0].toLong()
                val fileName = split[1]
                currentDirectory.addFile(File(fileName, fileLength))
            }
        }
        return currentDirectory
    }

    fun findWithAtMostSize(currentDirectory: Directory, maxSize: Long): Set<Directory> {
        val result = mutableSetOf<Directory>()
        if (currentDirectory.size <= maxSize) {
            result.add(currentDirectory)
        }
        for (child in currentDirectory.children) {
            result.addAll(findWithAtMostSize(child, maxSize))
        }
        return result
    }

    fun findWithAtMinSize(currentDirectory: Directory, minSize: Long): Set<Directory> {
        val result = mutableSetOf<Directory>()
        if (currentDirectory.size >= minSize) {
            result.add(currentDirectory)
        }
        for (child in currentDirectory.children) {
            result.addAll(findWithAtMinSize(child, minSize))
        }
        return result
    }

    fun part1(input: List<String>): Long {
        var currentDirectory = Directory(name = "/")
        var i = 0
        while (i < input.size) {
            val line = input[i]
            if (line.startsWith("$ cd")) {
                currentDirectory = processCDCommand(line, currentDirectory)
            } else if (line.startsWith("$ ls")) {
                var start = i + 1
                val dirData = mutableListOf<String>()
                while (start < input.size && !isCommand(input[start])) {
                    dirData.add(input[start])
                    start += 1
                }
                i = start - 1
                currentDirectory = processLSCommand(dirData, currentDirectory)
            }
            i += 1
        }
        // get to root directory
        while (currentDirectory.name != "/") {
            currentDirectory = currentDirectory.parent!!
        }
        val filteredDirectories = findWithAtMostSize(currentDirectory, 100000L)
        return filteredDirectories.sumOf { directory -> directory.size }
    }

    fun part2(input: List<String>): Long {
        var currentDirectory = Directory(name = "/")
        var i = 0
        while (i < input.size) {
            val line = input[i]
            if (line.startsWith("$ cd")) {
                currentDirectory = processCDCommand(line, currentDirectory)
            } else if (line.startsWith("$ ls")) {
                var start = i + 1
                val dirData = mutableListOf<String>()
                while (start < input.size && !isCommand(input[start])) {
                    dirData.add(input[start])
                    start += 1
                }
                i = start - 1
                currentDirectory = processLSCommand(dirData, currentDirectory)
            }
            i += 1
        }
        // get to root directory
        while (currentDirectory.name != "/") {
            currentDirectory = currentDirectory.parent!!
        }
        val requiredToFree = 30000000L - 70000000 + currentDirectory.size
        if (requiredToFree < 0) {
            return 0L
        }
        val filteredDirectories = findWithAtMinSize(currentDirectory, requiredToFree)
        val sorted = filteredDirectories.toList().sortedBy { directory -> directory.size }
        return sorted[0].size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)

    val input = readInput("Day07")
    part1(input).println()
    check(part2(testInput) == 24933642L)
    part2(input).println()
}
