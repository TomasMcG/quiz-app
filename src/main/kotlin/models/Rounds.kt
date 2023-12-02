package models


import persistence.Serializer
import utils.Utilities.emptyArrayList
import utils.Utilities.formatListString

data class Rounds(
    var roundId: Int = 0,
    var roundTitle: String,
    var questionsAttempted: Int = 0,
    var questions: ArrayList<Questions?> =emptyArrayList(),
    var isCompleted: Boolean = false

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




    fun updateQuestionId(questionToEdit: Questions?,newQuestionId: Int){questionToEdit?.questionId = newQuestionId}
    fun updateQuestionText(questionToEdit: Questions?,newQuestionText: String){questionToEdit?.questionText = newQuestionText}
    fun updateQuestionCorrectAnswer(questionToEdit: Questions?,newQuestionCorrectAnswer: String){questionToEdit?.correctAnswer = newQuestionCorrectAnswer}






}