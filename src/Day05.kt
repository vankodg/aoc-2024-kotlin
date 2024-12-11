fun main() {

    fun processRules(input: List<String>): Map<Int, List<Int>> {
        var rules = mutableMapOf<Int, MutableList<Int>>()
        input.forEach {
            val numbers = it.split("|").map { it.toInt() }
            val before = numbers[0]
            val after = numbers[1]
            (rules.getOrPut(before) { mutableListOf() }).add(after)
        }
        return rules
    }

    fun part1(input: List<String>): Int {
        var splittingAt = 0
        for ((i, line) in input.withIndex()) {
            if (line.isBlank()) {
                splittingAt = i
                break
            }
        }
        val rulesInput = input.subList(0, splittingAt)
        val updates = input.subList(splittingAt + 1, input.size)
        val rules = processRules(rulesInput)
        var sum = 0
        updates.forEach {
            val list = it.split(",").map { it.toInt() }
            for ((i, number) in list.withIndex()) {
                val rulesOfNumber = rules[number]
                if (rulesOfNumber != null) {
                    for (j in 0 until i) {
                        if (rulesOfNumber.contains(list[j])) {
                            return@forEach
                        }
                    }
                }
            }
            sum += list[(list.size - 1) / 2]
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var splittingAt = 0
        for ((i, line) in input.withIndex()) {
            if (line.isBlank()) {
                splittingAt = i
                break
            }
        }
        val rulesInput = input.subList(0, splittingAt)
        val updates = input.subList(splittingAt + 1, input.size)
        val rules = processRules(rulesInput)
        var sum = 0
        updates.forEach {
            var good = true
            val pageList = it.split(",").map { it.toInt() }
            outerLoop@ for ((i, number) in pageList.withIndex()) {
                val rulesOfNumber = rules[number]
                if (rulesOfNumber != null) {
                    for (j in 0 until i) {
                        if (rulesOfNumber.contains(pageList[j])) {
                            good = false
                            break@outerLoop
                        }
                    }
                }
            }
            if (!good) {
                val ruleCount = pageList.associateWith {
                    val rulesOfCurrent = rules[it]
                    if (rulesOfCurrent != null) {
                        rulesOfCurrent.filter { pageList.contains(it) }.size
                    } else {
                        0
                    }
                }
                sum += ruleCount.toList().sortedBy { it.second }[(pageList.size - 1) / 2].first
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    // check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
