package models

data class Rounds(
    var roundId: Int, var questionsAttempted: Int ,
    var questions: ArrayList<Questions>
) {

    private var lastQuestionId = 0
    private fun getQuestionId() = lastQuestionId++

    fun addQuestion(question: Questions): Boolean {
        question.questionId = getQuestionId()
        return questions.add(question)
    }

}