package models

/**
 * Data class representing a Question.
 *
 * This class encapsulates information about a question, including its identifier,
 * text, possible answers, correct answer, and difficulty level.
 *
 * @property questionId The unique identifier for the question.
 * @property questionText The text of the question.
 * @property possibleAnswers The list of possible answers for the question.
 * @property correctAnswer The correct answer for the question.
 * @property difficulty The difficulty level of the question.
 */
data class Questions(
    var questionId: Int,
    var questionText: String,
    var possibleAnswers: List<String>,
    var correctAnswer: String,
    var difficulty: String
) {

    /**
     * Generates a string representation of the question.
     *
     * @return A formatted string containing details of the question.
     */
    override fun toString(): String {
        val possibleAnswersString = possibleAnswers.joinToString("\n            ")
        return """ 
            questionId: $questionId
            difficulty: $difficulty
            question: $questionText
            possible answers: $possibleAnswersString
            correct answer: $correctAnswer
            
            """.trimIndent()
    }
}