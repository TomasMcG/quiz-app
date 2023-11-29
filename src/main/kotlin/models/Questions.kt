package models

data class Questions(
    var questionId: Int,
    var questionText: String ,
    var correctAnswer: String,
    var answer: List<String>
){}
