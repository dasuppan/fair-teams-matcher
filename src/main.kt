import java.io.File

const val SEPARATOR = ","
val EXPECTED_FORMAT_REGEX = Regex("\\w+($SEPARATOR)\\d{1,2}")

// Implements the Karmarkar-Karp algorithm
// Good read: http://bit-player.org/wp-content/extras/bph-publications/AmSci-2002-03-Hayes-NPP.pdf
fun main(args: Array<String>) {
    if (args.size != 2) throw Error("Specify the path for the file which holds the players as well as the desired number of teams!")
    val path = args[0];
    val numberOfTeams = args[1].toInt()
    val players = readPlayersFromFile(path).shuffled().sortedByDescending { p -> p.skill }
    val initialTuples = players.map { p ->
        val list = mutableListOf(mutableSetOf(p))
        repeat(numberOfTeams - 1) { list.add(mutableSetOf()) }
        list
    }.toMutableList()
    printTeams(partition(initialTuples)[0])
}

fun printTeams(teams: List<Set<Player>>) {
    teams.forEachIndexed { i, t ->
        println("TEAM " + (i+1) + " - TOTAL SKILL SCORE: ${calculateSetSum(t)}\n---------------")
        t.forEach { p -> println(p) }
        println("---------------")
    }
    println("GG HF!")
}

fun partition(playerTuples: MutableList<MutableList<MutableSet<Player>>>): MutableList<MutableList<MutableSet<Player>>> {
    if (playerTuples.size == 1) return playerTuples
    var firstTuple = Pair(playerTuples[0], 0)
    var secondTuple = Pair(playerTuples[1], 0)
    for (tuple: MutableList<MutableSet<Player>> in playerTuples) {
        var max = Int.MIN_VALUE;
        var min = Int.MAX_VALUE
        for (playerSet: MutableSet<Player> in tuple) {
            val setSum = calculateSetSum(playerSet)
            if (setSum > max) max = setSum
            if (setSum < min) min = setSum
        }
        val maxDifference = max - min
        if (maxDifference > firstTuple.second && secondTuple.first != tuple) {
            firstTuple = Pair(tuple, maxDifference)
        } else if (maxDifference > secondTuple.second) {
            secondTuple = Pair(tuple, maxDifference)
        }
    }

    playerTuples.remove(firstTuple.first)
    playerTuples.remove(secondTuple.first)

    firstTuple = Pair(
        firstTuple.first.sortedBy { set: MutableSet<Player> -> calculateSetSum(set) }.toMutableList(),
        firstTuple.second
    )
    secondTuple =
        Pair(
            secondTuple.first.sortedByDescending { set: MutableSet<Player> -> calculateSetSum(set) }.toMutableList(),
            secondTuple.second
        )

    val newTuple: MutableList<MutableSet<Player>> = firstTuple.first
    for (i in newTuple.indices) {
        newTuple[i].addAll(secondTuple.first[i])
    }

    playerTuples.add(newTuple)
    return partition(playerTuples)
}

fun calculateSetSum(set: Set<Player>): Int {
    return set.fold(0, { sum, p -> sum + p.skill })
}

fun readPlayersFromFile(fileName: String): List<Player> {
    return File(fileName).readLines().mapIndexed { index, line ->
        if (!line.matches(EXPECTED_FORMAT_REGEX)) throw Error("Line " + (index + 1) + " is malformed! Expected format: NAME,SKILL IN RANGE 0-99")
        line.split(SEPARATOR).let { Player(it[0], it[1].toInt()) }
    }
}