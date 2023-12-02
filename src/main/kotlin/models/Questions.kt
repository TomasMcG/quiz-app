package models

data class Questions(
    var questionId: Int,
    var questionText: String ,
    var possibleAnswers: List<String>,
    var correctAnswer: String,
    var difficulty: String,

){

    override fun toString():String {
        val possibleAnswersString = possibleAnswers.joinToString("\n            ")
       return """ 
            questionId: $questionId
            difficulty: $difficulty
            question: $questionText
            possible answers: $possibleAnswersString
            correct answer: $correctAnswer
            
            """".trimIndent()
}
}
