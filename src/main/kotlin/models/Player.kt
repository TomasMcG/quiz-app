package models

/**
 * Data class representing a Player.
 *
 * This class encapsulates information about a player, including their identifier, name,
 * associated rounds, and the number of attempts made.
 *
 * @property playerId The unique identifier for the player.
 * @property name The name of the player.
 * @property rounds The list of rounds associated with the player.
 * @property noAttempts The number of attempts made by the player.
 */
data class Player(
    var playerId: Int,
    var name: String,
    var rounds: List<Rounds>,
    var noAttempts: Int
) {
}