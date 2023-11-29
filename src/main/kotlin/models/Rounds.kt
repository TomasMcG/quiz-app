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

    fun findOne(id: Int): Questions? {
        return questions.find { question -> question.questionId == id }
    }

    fun deleteQuestions(id: Int): Boolean {
        return questions.removeIf { question -> question.questionId == id }
    }


    fun updateQuestions(id: Int, newQuestions: Questions): Boolean {
        val foundQuestions = findOne(id)  //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundQuestions != null) {
            foundQuestions.questionId = newQuestions.questionId
            foundQuestions.questionText = newQuestions.questionText
            foundQuestions.correctAnswer = newQuestions.correctAnswer
            foundQuestions.answer = newQuestions.answer
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    /*fun updateQuestionsId(id: Int, newQuestions: Questions): Boolean {
        val foundQuestions = findOne(id)  //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundQuestions != null) {
            foundQuestions.itemContents = newQuestions.questionContents
            foundQuestions.isItemComplete = newQuestions.isItemComplete
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }
    change how update works later.
    */

}