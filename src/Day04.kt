fun main() {
    fun part1(input: List<String>): Int {
        var numberOfXmas = 0
        for (i in 0 until input.size) {
            for (j in 0 until input[i].length) {
                // #1 & #5 direction, horizontal left to right, right to left
                if (j + 3 < input[i].length) {
                    if (input[i].substring(j, j + 4) == "XMAS"
                        || input[i].substring(j, j + 4) == "XMAS".reversed()
                    ) {
                        numberOfXmas++
                    }
                }
                // #2 & #6 direction, diagonal upper left to lower right
                if (i + 3 < input.size && j + 3 < input[i].length) {
                    val subString =
                        input[i][j].toString() + input[i + 1][j + 1] + input[i + 2][j + 2] + input[i + 3][j + 3]
                    if (subString == "XMAS"
                        || subString == "XMAS".reversed()
                    ) {
                        numberOfXmas++
                    }
                }
                // #3 & #7 direction, vertical
                if (i + 3 < input.size) {
                    val subString = input[i][j].toString() + input[i + 1][j] + input[i + 2][j] + input[i + 3][j]
                    if (subString == "XMAS"
                        || subString == "XMAS".reversed()
                    ) {
                        numberOfXmas++
                    }
                }
                // #4 & #8 direction, diagonal upper left to lower right
                if (i + 3 < input.size && j >= 3) {
                    val subString =
                        input[i][j].toString() + input[i + 1][j - 1] + input[i + 2][j - 2] + input[i + 3][j - 3]
                    if (subString == "XMAS"
                        || subString == "XMAS".reversed()
                    ) {
                        numberOfXmas++
                    }
                }
            }
        }
        return numberOfXmas
    }

    fun part2(input: List<String>): Int {
        var numberOfXmas = 0
        for (i in 1 until input.size - 1) {
            for (j in 1 until input[i].length - 1) {
                if (input[i][j] != 'A') continue
                // first MAS, upper left to lower right
                val firstMAS = input[i - 1][j - 1].toString() + input[i][j] + input[i + 1][j + 1]
                val secondMAS = input[i + 1][j - 1].toString() + input[i][j] + input[i - 1][j + 1]
                if ((firstMAS == "MAS" || firstMAS == "MAS".reversed())
                    && (secondMAS == "MAS" || secondMAS == "MAS".reversed())
                ) {
                    numberOfXmas++
                }
            }
        }
        return numberOfXmas
    }

    // Test if implementation meets criteria from the description, like:
    // check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    part2(testInput).println()
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
