package models


import persistence.Serializer
import utils.Utilities.emptyArrayList
import utils.Utilities.formatListString

/**
 * Data class representing a Round.
 *
 * This class encapsulates information about a round, including its identifier, title,
 * number of questions attempted, associated questions, and completion status.
 *
 * @property roundId The unique identifier for the round.
 * @property roundTitle The title of the round.
 * @property questionsAttempted The number of questions attempted in the round.
 * @property questions The list of questions associated with the round.
 * @property isCompleted The completion status of the round.
 */
data class Rounds(
    var roundId: Int = 0,
    var roundTitle: String,
    var questionsAttempted: Int = 0,
    var questions: ArrayList<Questions?> = ArrayList(),
    var isCompleted: Boolean = false
) {

    /**
     * Generates a string representation of the round.
     *
     * @return A formatted string containing details of the round.
     */
    override fun toString() =
        if (isCompleted)
            "\n $roundId: $roundTitle (Completed) \n Number of Attempts: $questionsAttempted \n"
        else
            "\n $roundId: $roundTitle (Incomplete) \n Number of Attempts: $questionsAttempted \n }"

    // ---------------------------------------------- API functions for Questions objects below.

    // Incrementer for question id
    private var lastQuestionId = 1

    /**
     * Generates and returns a unique identifier for a question.
     *
     * @return A unique identifier for a question.
     */
    private fun getQuestionId() = lastQuestionId++

    /**
     * Adds a question to the list of questions associated with the round.
     *
     * @param question The question to be added.
     * @return `true` if the question is added successfully, `false` otherwise.
     */
    fun addQuestion(question: Questions): Boolean {
        question.questionId = getQuestionId()
        return questions.add(question)
    }

    /**
     * Returns the number of questions associated with the round.
     *
     * @return The number of questions associated with the round.
     */
    fun numberOfQuestions() = questions.size

    /**
     * Finds and returns a question by its identifier.
     *
     * @param id The identifier of the question to find.
     * @return The found question, or `null` if no question is found.
     */
    fun findQuestion(id: Int): Questions? {
        return questions.find { question -> question?.questionId == id }
    }

    /**
     * Deletes a question by its identifier.
     *
     * @param id The identifier of the question to be deleted.
     * @return `true` if the question is deleted successfully, `false` otherwise.
     */
    fun deleteQuestions(id: Int): Boolean {
        return questions.removeIf { question -> question?.questionId == id }
    }

    /**
     * Returns a string representation of all questions associated with the round.
     *
     * @return A string containing the details of all questions associated with the round.
     */
    fun listAllQuestions(): String =
        if (questions.isEmpty())
            "No questions stored"
        else
            formatListString(questions as ArrayList<Any>)

    /**
     * Updates the identifier of a question.
     *
     * @param questionToEdit The question to be edited.
     * @param newQuestionId The new identifier for the question.
     */
    fun updateQuestionId(questionToEdit: Questions?, newQuestionId: Int) {
        questionToEdit?.questionId = newQuestionId
    }

    /**
     * Updates the text of a question.
     *
     * @param questionToEdit The question to be edited.
     * @param newQuestionText The new text for the question.
     */
    fun updateQuestionText(questionToEdit: Questions?, newQuestionText: String) {
        questionToEdit?.questionText = newQuestionText
    }

    /**
     * Updates the correct answer of a question.
     *
     * @param questionToEdit The question to be edited.
     * @param newQuestionCorrectAnswer The new correct answer for the question.
     */
    fun updateQuestionCorrectAnswer(questionToEdit: Questions?, newQuestionCorrectAnswer: String) {
        questionToEdit?.correctAnswer = newQuestionCorrectAnswer
    }
}