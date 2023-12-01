package models


import utils.Utilities.emptyArrayList
import utils.Utilities.formatListString

data class Rounds(
    var roundId: Int = 0,
    var roundTitle: String,
    var questionsAttempted: Int = 0,
    var questions: ArrayList<Questions?> =emptyArrayList()
) {
    //----------------------------------------------api function for questions objects below.
    //incrementer for question id
    private var lastQuestionId = 1
    private fun getQuestionId() = lastQuestionId++

    fun addQuestion(question: Questions): Boolean {
        question.questionId = getQuestionId()
        return questions.add(question)
    }


    fun numberOfQuestions() = questions.size

    fun findQuestion(id: Int): Questions? {
        return questions.find { question -> question?.questionId == id }
    }

    fun deleteQuestions(id: Int): Boolean {
        return questions.removeIf{ question -> question?.questionId == id }
    }

    fun listAllQuestions(): String =
        if (questions.isEmpty())   //can remove curly braces for single line of code for if
            "No questions stored"
        else
            formatListString(questions as ArrayList<Any>)
//cast it as any so it can use the utility.


    fun updateQuestions(id: Int, newQuestions: Questions): Boolean {
        val foundQuestions = findQuestion(id)  //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundQuestions != null) {
            foundQuestions.questionId = newQuestions.questionId
            foundQuestions.questionText = newQuestions.questionText
            foundQuestions.correctAnswer = newQuestions.correctAnswer
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    /*fun updateQuestionsId(id: Int, newQuestions: Questions): Boolean {
        val foundQuestions = findQuestion(id)  //if the object exists, use the details passed in the newItem parameter to
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