package models

data class Rounds(
    var roundId: Int, var questionsAttempted: Int , var questions: ArrayList<Questions>
){}
