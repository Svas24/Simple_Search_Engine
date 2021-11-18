package search

import java.io.File

fun main(args: Array<String>) {
    val lines = File(args[args.indexOf("--data") + 1]).readLines()

    val idxMap = mutableMapOf<String, Set<Int>>()
    lines.forEachIndexed { id, line -> line.uppercase().split(" ").forEach {
                idxMap[it] = idxMap.getOrDefault(it, emptySet()) + setOf(id)}}

    while (true) {
        println("\n=== Menu ===\n1. Search information.\n2. Print all data.\n0. Exit.")
        when (readLine()!!.toInt()) {
            1 -> {
                println("Select a matching strategy: ALL, ANY, NONE")
                val searchStrategy = readLine()
                fun operation(set1: Set<Int>, set2: Set<Int>) = when (searchStrategy) {
                    "ANY" -> set1.union(set2)
                    "ALL" -> set1.intersect(set2)
                    else ->  set1.minus(set2)  //  "NONE"
                }
                println("Enter a name or email to search all suitable people.")
                val searchWords = readLine()!!.uppercase().split(" ").toSet()
                var resultIds = if (searchStrategy == "ANY") emptySet<Int>() else lines.indices.toSet()
                for (word in searchWords) resultIds = operation(resultIds, idxMap.getOrDefault(word, emptySet<Int>()))
                resultIds.forEach { println(lines[it]) }
                if (resultIds.isEmpty()) println("No matching people found.")
            }
            2 -> println ("=== List of people ===\n${lines.joinToString ("\n")}")
            else -> break  // 0
        }
    }
    println("Bye!")
}