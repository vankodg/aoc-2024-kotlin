fun main() {
    fun part1(input: String): Int {
        var sum = 0
        val regex = "mul\\(([0-9]+),([0-9]+)\\)".toRegex()
        regex.findAll(input).forEach {
            val (a, b) = it.destructured
            sum += a.toInt() * b.toInt()
        }

        return sum
    }

    fun part2(input: String): Int {
        var sum = 0
        val regexMul = "mul\\(([0-9]+),([0-9]+)\\)".toRegex()
        val regexDont = "don't\\(\\)".toRegex()
        val regexDo = "do\\(\\)".toRegex()
        val indexDont = regexDont.find(input)?.range?.last
        if (indexDont != null) {
            regexMul.findAll(input.substring(0..indexDont)).forEach {
                val (a, b) = it.destructured
                sum += a.toInt() * b.toInt()
            }
            val indexDo = regexDo.find(input.substring(indexDont))?.range?.last
            if (indexDo != null) {
                sum += part2(input.substring(indexDont+indexDo))
            }
        } else {
            regexMul.findAll(input).forEach {
                val (a, b) = it.destructured
                sum += a.toInt() * b.toInt()
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    check(part1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))") == 161)
    check(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))") == 48)

    // Or read a large test input from the `src/Day01_test.txt` file:
    //val testInput = readInput("Day03_test")
    //check(part1(testInput) == 2)
    //check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03").joinToString()
    part1(input).println()
    part2(input).println()
}
