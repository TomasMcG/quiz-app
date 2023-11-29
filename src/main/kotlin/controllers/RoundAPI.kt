package controllers

import models.Rounds
import persistence.Serializer
import utils.Utilities.formatListString

class RoundAPI (serializer: Serializer){
    private var serializer: Serializer = serializer
    private var rounds = ArrayList<Rounds>()
    private var lastId = 0
    private fun getId() = lastId++

    fun add(roundToAdd: Rounds): Boolean {
        roundToAdd.roundId = getId()
        return rounds.add(roundToAdd)
    }
    fun listAllNotes(): String =
        if (rounds.isEmpty())   //can remove curly braces for single line of code for if
            "No notes stored"
        else
            formatListString(rounds)


    @Throws(Exception::class)
    fun load() {
        rounds = serializer.read() as ArrayList<Rounds>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(rounds)
    }

}