enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    operator fun inc(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun getValue(): Pair<Int, Int> {
        return when (this) {
            NORTH -> Pair(-1, 0)
            EAST -> Pair(0, 1)
            SOUTH -> Pair(1, 0)
            WEST -> Pair(0, -1)
        }
    }
}

fun main() {
    fun isInBound(position: Pair<Int, Int>, input: List<String>): Boolean {
        return (position.first in input.indices) && (position.second in (0 until input[position.first].length))
    }

    fun part1(input: List<String>): Int {
        var currentDirection = Direction.NORTH
        var currentPosition = 0 to 0
        val visited = mutableSetOf<Pair<Int, Int>>()
        for ((i, line) in input.withIndex()) {
            if (line.contains('^')) {
                currentPosition = i to line.indexOf('^')
                break
            }
        }
        while (isInBound(currentPosition, input)) {
            visited.add(currentPosition)
            var nextPosition = currentPosition + currentDirection
            while (isInBound(nextPosition, input) && input[nextPosition.first][nextPosition.second] == '#') {
                currentDirection++
                nextPosition = currentPosition + currentDirection
            }
            currentPosition = nextPosition
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        var startingDirection = Direction.NORTH
        var startingPosition = 0 to 0
        for ((i, line) in input.withIndex()) {
            if (line.contains('^')) {
                startingPosition = i to line.indexOf('^')
                break
            }
        }
        var count = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#' || (i == startingPosition.first && j == startingPosition.second)) {
                    continue
                }
                val newMap = input.toMutableList()
                newMap[i] = newMap[i].mapIndexed { index, c -> if (index == j) '#' else c }.joinToString("")
                var currentDirection = startingDirection
                var currentPosition = startingPosition
                val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()

                while (isInBound(currentPosition, newMap)) {
                    if (visited.contains(currentPosition to currentDirection)) {
                        count++
                        break
                    }
                    visited.add(currentPosition to currentDirection)
                    var nextPosition = currentPosition + currentDirection
                    while (isInBound(nextPosition, input) && newMap[nextPosition.first][nextPosition.second] == '#') {
                        currentDirection++
                        nextPosition = currentPosition + currentDirection
                    }
                    currentPosition = nextPosition
                }
            }
        }
        return count
    }

    // Test if implementation meets criteria from the description, like:
    // check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private operator fun Pair<Int, Int>.plus(value: Direction): Pair<Int, Int> {
    return this.first + value.getValue().first to this.second + value.getValue().second
}
