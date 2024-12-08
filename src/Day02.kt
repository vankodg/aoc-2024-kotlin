import kotlin.math.abs

fun isReportSafe(report: List<Int>): Boolean {
    var safe = true
    if (report.size >= 2) {
        val isAscending = report[0] < report[1]
        for (i in 0 until report.lastIndex) {
            if (abs(report[i] - report[i + 1]) > 3) {
                safe = false
                break
            } else if (isAscending) {
                if (report[i] >= report[i + 1]) {
                    safe = false
                    break
                }
            } else if (report[i] <= report[i + 1]) {
                safe = false
                break
            }
        }
    }
    return safe
}

fun main() {
    fun part1(input: List<String>): Int {
        var safeReports = 0
        input.forEach { line ->
            val levels = line.split(" ").map { it.toInt() }
            if (isReportSafe(levels)) {
                safeReports++
            }
        }
        return safeReports
    }

    fun part2(input: List<String>): Int {
        var safeReports = 0
        input.forEach { line ->
            val levels = line.split(" ").map { it.toInt() }
            if (isReportSafe(levels)) {
                safeReports++
            } else for (i in 0 until levels.size) {
                if (isReportSafe(levels.filterIndexed { index, _ -> index != i })) {
                    safeReports++
                    break
                }
            }
        }
        return safeReports
    }

    // Test if implementation meets criteria from the description, like:
    check(part2(listOf("58 56 55 52 49 48 45 43")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
