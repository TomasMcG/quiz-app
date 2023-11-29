package controllers

import models.Rounds

class RoundAPI {
    private var rounds = ArrayList<Rounds>()
    private var lastId = 0
    private fun getId() = lastId++

    fun add(roundToAdd: Rounds): Boolean {
        roundToAdd.roundId = getId()
        return rounds.add(roundToAdd)
    }


}