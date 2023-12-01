package controllers

import models.Rounds
import persistence.Serializer
import utils.Utilities
import utils.Utilities.formatListString

class RoundAPI (serializerType: Serializer){
    private var serializer: Serializer = serializerType
    private var rounds = ArrayList<Rounds>()
    private var lastId = 1
    private fun getId() = lastId++

    fun add(roundToAdd: Rounds): Boolean {
        roundToAdd.roundId = getId()
        return rounds.add(roundToAdd)
    }
    fun listAllRounds(): String =
        if (rounds.isEmpty())   //can remove curly braces for single line of code for if
            "No rounds stored"
        else
            formatListString(rounds)
    fun numberOfRounds(): Int { return rounds.size}

    fun deleteRound(idToDelete: Int): Rounds?{
            val roundToDelete = rounds.find {it.roundId == idToDelete}
            return if (roundToDelete != null){
                rounds.remove(roundToDelete)
                roundToDelete
            } else null

    }
    fun isValidIndex(roundId: Int): Boolean {
        // Assuming `rounds` is the list of objects with a `roundId` attribute
        val validRoundIds = rounds.map { it.roundId }
        return roundId in validRoundIds
    }

    fun findRounds(roundId: Int): Rounds? {
        return rounds.find { it.roundId == roundId }
    }


    fun updateRoundTitle(roundToEdit: Rounds?,newTitle: String){
        roundToEdit?.roundTitle = newTitle
    }
    fun updateRoundId(roundToEdit: Rounds?,newRoundId: Int){roundToEdit?.roundId = newRoundId}
    fun updateQuestionsAttempted(roundToEdit: Rounds?, newNoQuestionsAttempted: Int){roundToEdit?.questionsAttempted = newNoQuestionsAttempted
    }


    @Throws(Exception::class)
    fun load() {
        rounds = serializer.read() as ArrayList<Rounds>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(rounds)
    }

}