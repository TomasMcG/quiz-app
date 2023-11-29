package models

data class Player(
    var playerId: Int,
    var name: String,
    var rounds: List<Rounds>,
    var noAttemps: Int){


}
