package models

data class Questions(
    var questionId: Int,
    var questionText: String ,
    var possibleAnswers: List<String>,
    var correctAnswer: String,
    var difficulty: String,

){}
