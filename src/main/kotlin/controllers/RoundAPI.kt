package controllers

import models.Rounds
import persistence.Serializer
import utils.Utilities
import utils.Utilities.formatListString

class RoundAPI (serializerType: Serializer){
    private var serializer: Serializer = serializerType
    private var rounds = ArrayList<Rounds>()
    private var lastId = 0
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

    fun deleteRound(indexToDelete: Int): Rounds?{

            return if (Utilities.isValidListIndex(indexToDelete, rounds)){
                rounds.removeAt(indexToDelete)
            } else null

    }
    fun isValidIndex(index: Int) : Boolean{
        return Utilities.isValidListIndex(index, rounds)
    }

    fun findRounds(index: Int): Rounds?{


        return if(Utilities.isValidListIndex(index, rounds)){
            rounds[index]
        }
        else null



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