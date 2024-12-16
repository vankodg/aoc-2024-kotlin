import java.math.BigInteger
import kotlin.math.pow

fun main() {
    fun isSolvable(sum: BigInteger, terms: List<BigInteger>): Boolean {
        var rangeMax = BigInteger.ZERO
        var rangeMin = BigInteger.ZERO
        for (term in terms) {
            if (rangeMax == BigInteger.ZERO && rangeMin == BigInteger.ZERO) {
                rangeMax = term
                rangeMin = term
            } else if (term == BigInteger.ONE || rangeMin == BigInteger.ONE && rangeMax == BigInteger.ONE) {
                rangeMax += term
                rangeMin *= term
            } else {
                rangeMax *= term
                rangeMin += term
            }
        }
        if (sum < rangeMin || sum > rangeMax) {
            return false
        }
        for (i in 0 until 2.0.pow(terms.size - 1).toInt()) {
            var actSum = terms[0]
            for (j in 1 until terms.size) {
                if ((1 shl j - 1) and i > 0) {
                    actSum *= terms[j]
                } else {
                    actSum += terms[j]
                }
            }
            if (sum == actSum) {
                return true
            }
        }
        return false
    }

    fun isSolvable2(sum: BigInteger, terms: List<BigInteger>): Boolean {
        var rangeMax = BigInteger.ZERO
        var rangeMin = BigInteger.ZERO
        for (i in 0 until 3.0.pow(terms.size - 1).toInt()) {
            var actSum = terms[0]
            for (j in 1 until terms.size) {
                val whichOperator = i / (3.0.pow(j - 1).toInt()) % 3
                when (whichOperator) {
                    0 -> actSum += terms[j]
                    1 -> actSum *= terms[j]
                    2 -> actSum = (actSum.toString() + terms[j].toString()).toBigInteger()
                }
            }
            if (sum == actSum) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): BigInteger {
        var total = BigInteger.ZERO
        for (line in input) {
            val numbers = line.split(":", " ").filter { it.isNotEmpty() }.map { it.toBigInteger() }
            val sum = numbers[0]
            val terms = numbers.subList(1, numbers.size)
            if (isSolvable(sum, terms)) {
                total += sum
            }
        }
        return total
    }

    fun part2(input: List<String>): BigInteger {
        var total = BigInteger.ZERO
        for (line in input) {
            val numbers = line.split(":", " ").filter { it.isNotEmpty() }.map { it.toBigInteger() }
            val sum = numbers[0]
            val terms = numbers.subList(1, numbers.size)
            if (isSolvable(sum, terms) || isSolvable2(sum, terms)) {
                total += sum
            }
        }
        return total
    }

    // Test if implementation meets criteria from the description, like:
    // check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == BigInteger.valueOf(3749))
    check(part2(testInput) == BigInteger.valueOf(11387))

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}