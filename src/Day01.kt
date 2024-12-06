import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val listLeft = mutableListOf<Int>()
        val listRight = mutableListOf<Int>()
        for (line in input) {
            val numbers = line.split(" ").filter{it.isNotEmpty()}
            listLeft.add(numbers[0].toInt())
            listRight.add(numbers[1].toInt())
        }
        listLeft.sort()
        listRight.sort()
        var sum = 0
        for (i in listLeft.indices) {
            sum += abs(listLeft[i] - listRight[i])
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val mapLeft = mutableMapOf<Int, Int>()
        val mapRight = mutableMapOf<Int, Int>()
        for (line in input) {
            val numbers = line.split(" ").filter{it.isNotEmpty()}
            val valueLeft = mapLeft[numbers[0].toInt()]
            if (valueLeft != null) {
                mapLeft[numbers[0].toInt()] = valueLeft + 1
            } else {
                mapLeft[numbers[0].toInt()] = 1
            }
            val valueRight = mapRight[numbers[1].toInt()]
            if (valueRight != null) {
                mapRight[numbers[1].toInt()] = valueRight + 1
            } else {
                mapRight[numbers[1].toInt()] = 1
            }
        }
        var sum = 0
        for (number in mapLeft.keys) {
            val counts = mapRight[number] ?: 0
            sum += number * counts * mapLeft[number]!!
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    part2(testInput).println()
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
