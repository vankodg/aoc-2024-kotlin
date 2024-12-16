fun main() {
    fun processInput(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val freqMapList: MutableMap<Char, MutableList<Pair<Int, Int>>> = mutableMapOf()
        input.forEachIndexed { rowIndex, line ->
            line.toCharArray().forEachIndexed { colIndex, char ->
                if (char != '.') {
                    val newList = freqMapList.getOrDefault(char, mutableListOf())
                    newList.add(rowIndex to colIndex)
                    freqMapList[char] = newList
                }
            }
        }
        return freqMapList
    }

    fun isInbounds(pair : Pair<Int, Int>, rows: Int, cols: Int) : Boolean {
        return pair.first in 0 until rows && pair.second in 0 until cols
    }

    fun part1(input: Map<Char, List<Pair<Int, Int>>>, rows: Int, cols: Int): Int {
        val antinodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (freq in input) {
            freq.value.forEachIndexed { index, pair ->
                freq.value.subList(index + 1, freq.value.size).forEach { pair2 ->
                    val antinode1 = 2 * pair.first - pair2.first to 2 * pair.second - pair2.second
                    val antinode2 = 2 * pair2.first - pair.first to 2 * pair2.second - pair.second
                    if (isInbounds(antinode1,rows,cols)) {
                        antinodes.add(antinode1)
                    }
                    if (isInbounds(antinode2,rows,cols)) {
                        antinodes.add(antinode2)
                    }
                }
            }
        }
        return antinodes.size
    }

    fun part2(input: Map<Char, List<Pair<Int, Int>>>, rows: Int, cols: Int): Int {
        val antinodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (freq in input) {
            freq.value.forEachIndexed { index, pair ->
                freq.value.subList(index + 1, freq.value.size).forEach { pair2 ->
                    val diff = pair2 - pair
                    var antinode1 = pair
                    while (isInbounds(antinode1,rows,cols)) {
                        antinodes.add(antinode1)
                        antinode1 -= diff
                    }
                    var antinode2 = pair + diff
                    while (isInbounds(antinode2,rows,cols)) {
                        antinodes.add(antinode2)
                        antinode2 += diff
                    }
                }
            }
        }
        return antinodes.size
    }

    // Test if implementation meets criteria from the description, like:
    // check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    val testInputMap = processInput(testInput)
    check(part1(testInputMap, testInput.size, testInput[0].length) == 14)
    check(part2(testInputMap, testInput.size, testInput[0].length) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    val inputMap = processInput(input)
    part1(inputMap, input.size, input[0].length).println()
    part2(inputMap, input.size, input[0].length).println()
}

private operator fun Pair<Int, Int>.minus(diff: Pair<Int, Int>): Pair<Int, Int> {
    return this.first - diff.first to this.second - diff.second
}

private operator fun Pair<Int, Int>.plus(diff: Pair<Int, Int>): Pair<Int, Int> {
    return this.first + diff.first to this.second + diff.second
}
