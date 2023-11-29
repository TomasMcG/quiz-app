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

    fun numberOfQuestions() = questions.size

    fun findOne(id: Int): Questions?{
        return questions.find{ question -> question.questionId == id }
    }

    fun delete(id: Int): Boolean {
        return questions.removeIf { question -> question.questionId == id}
    }

}