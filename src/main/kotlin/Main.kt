import controllers.RoundAPI
import models.Questions
import models.Rounds
import mu.KotlinLogging
import persistence.XMLSerializer

import utils.ScannerInput
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
private val roundAPI = RoundAPI(XMLSerializer(File("rounds.xml")))
//private val questionAPI = Rounds(XMLSerializer(File("roundsQuestions.xml")))
fun main(args: Array<String>) {
    runMenu()

}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |         Admin CRUD Menu         
         > ----------------------------------
         > | Player MENU                      
         > |   1) Add a Player                
         > |   2) List all Players            
         > |   3) Update a Player             
         > |   4) Delete a Player             
         > ----------------------------------
         > | Questions MENU                      
         > |   5) Add a Question                
         > |   6) List all Questions            
         > |   7) Update a Question             
         > |   8) Delete a Question             
         > ----------------------------------
         > | Round MENU                      
         > |   9) Edit Round values menu         
         > ----------------------------------         
         > |   0) Exit                      
         > ----------------------------------
         > ==>> """.trimMargin(">"))

}

fun runMenu(){
    do{
        val option = mainMenu()
        when(option){
           // 1 -> managePlayer()
            1 -> addPlayer()
            2 -> listPlayers()
            3 -> updatePlayer()
            4 -> deletePlayer()
            5 -> addQuestionToRound()
            6 -> listQuestions()
            7 -> updateQuestion()
            8 -> deleteQuestion()
            9 -> runRoundMenu()

            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    }while(true)

}

fun runRoundMenu(){
    do{
        val option = roundMenu()
        when(option){
            1 -> addRound()
            2 -> println(roundAPI.listAllRounds())
            3 -> updateRound()
            4  -> deleteRound()
            5 -> saveRound()
            6 -> loadRound()

            0 -> exitApp()
            -1 -> println("Going back to main menu")
            else -> println("Invalid option entered: ${option}")
        }
    }while(option != 0 && option != -1)

}

fun roundMenu()
    : Int {
        return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |         Round CRUD Menu         
         > ----------------------------------              
         > |   1) Add a Round                
         > |   2) List all Rounds            
         > |   3) Update a Round             
         > |   4) Delete a Round      
         > |   5) Save Rounds
         > |   6) Load Rounds
         >      
         > ----------------------------------         
         > |   0) Exit   
         >    -1)Back to Main Menu               
         > ----------------------------------
         > ==>> """.trimMargin(">"))

    }


/*
fun managePlayer(){
    do{
        val option = mainMenu()
        when(option){
            1 -> addPlayer()
            2 -> listPlayers()
            3 -> updatePlayer()
            4 -> deletePlayer()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    }while(true)

}
do this later*/

fun addPlayer(){
    logger.info{"addPlayer() function invoked"}
   /* var playerId = ScannerInput.readNextInt("Enter a title for the note: ")
    var name = ScannerInput.readNextInt(Note(noteTitle,priority,category,false, LocalDate.now(),LocalDate.now(),noteContent,noteStatus))
    //var rounds = ScannerInput.readNextInt List<Rounds>
    var noAttempts = ScannerInput.readNextInt("enter number of attempts")
    val isAdded = playerAPI.add(Player(playerId,name,rounds,noAttemps))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }*/

    //add api and controllers back in.
}


fun listPlayers(){
    logger.info{"listPlayers() function invoked"}
}

fun updatePlayer(){
    logger.info{"updatePlayer() function invoked"}}

fun deletePlayer(){
    logger.info{"deletePlayer() function invoked"}
}

//-------------------------------------------------------------------
private fun addQuestionToRound() {
    val round: Rounds? = askUserToChooseRound()
    if (round != null) {
        if (round.addQuestion(Questions(1,
                questionText = ScannerInput.readNextLine("\t Question and possible answers: "),
                correctAnswer = ScannerInput.readNextLine("\t Correct Answer: ")

            )))

            println("Add Successful!")
        else println("Add NOT Successful")
    }
}


private fun askUserToChooseRound(): Rounds? {
    println(roundAPI.listAllRounds())
    if ( roundAPI.numberOfRounds() > 0) {
        val round = roundAPI.findRounds(ScannerInput.readNextInt("\nEnter the id of the round you want to whose questions you want to deal with: "))
        if (round != null) {
            return round
        } else {
            println("Round id is not valid")
        }
    }
    return null //selected note is not active
}

fun deleteQuestion(){
    logger.info{"deleteQuestion() function invoked"}

        val round: Rounds? = askUserToChooseRound()
        if (round != null) {
            val question: Questions? = askUserToChooseQuestion(round)
            if (question != null) {
                val isDeleted = round.deleteQuestions(question.questionId)
                if (isDeleted) {
                    println("Delete Successful!")
                } else {
                    println("Delete NOT Successful")
                }
            }
        }
}

private fun askUserToChooseQuestion(round: Rounds): Questions? {
    if (round.numberOfQuestions() > 0) {
        print(round.listAllQuestions())
        return round.findQuestion(ScannerInput.readNextInt("\nEnter the id of the Question to deal with: "))
    }
    else{
        println ("No questions for chosen Round")
        return null
    }
}


fun listQuestions(){
    logger.info{"listQuestions() function invoked"}
    val round = askUserToChooseRound()
    if (round != null) {
        val question: ArrayList<Questions?> = round.questions
        if (question != null) {
            println(round.listAllQuestions())

        }
  }
}

fun updateQuestion(){
    logger.info{"updateQuestion() function invoked"}
    val round: Rounds? = askUserToChooseRound()
    if (round != null) {
        val questionToEdit: Questions? = askUserToChooseQuestion(round)
        if (questionToEdit != null) {
          //what questio ndo you want to edit

            var option: Int
            do{option = questionAttributeMenu(questionToEdit)
                when(option){
                    1 -> {var newQuestionText: String = ScannerInput.readNextLine("Please enter the new question text")
                        round.updateQuestionText(questionToEdit,newQuestionText)}
                    2 ->{ var newCorrectAnswer: String = ScannerInput.readNextLine("Please enter the new correct answer")
                        round.updateQuestionCorrectAnswer(questionToEdit,newCorrectAnswer)}
                    3 ->{ var newQuestionId: Int = ScannerInput.readNextInt("Please enter the new question id")
                        round.updateQuestionId(questionToEdit,newQuestionId)}
                    99 -> updateQuestion()
                    100 -> updateRound()
                    0 -> println("exiting")
                    //Eventually put in update questions here
                }}while(option != 99 && option != 100 && option != 0)
        }
    }
}

fun questionAttributeMenu(questionToEdit:Questions ):Int = ScannerInput.readNextInt(
    """
        Please Choose the attribute you would like to update
        1.Question text: ${questionToEdit.questionText}
        2. Question Correct Answe: ${questionToEdit.correctAnswer}
        3. Question id: ${questionToEdit.questionId}
        99. Choose a different Question to update
        100. Choose a different round to update
        0. Exit to main menu
    """.trimIndent()
)

//------------------------------------------------------------------------------------
fun addRound(){
    logger.info{"addRound() function invoked"}
    //these info functions are lambdas
    val roundTitle = ScannerInput.readNextLine("Enter a title for the round: ")
    var questionsAttempted = ScannerInput.readNextInt("Enter the number of attempts: ")
    val isAdded = roundAPI.add(Rounds(roundTitle = roundTitle, questionsAttempted = questionsAttempted))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}





fun updateRound() {
    logger.info { "updateRound() function invoked" }
    /*println(roundAPI.listAllRounds())
    if (roundAPI.numberOfRounds() > 0) {
        val indexToUpdate =
            ScannerInput.readNextInt("Please choose the id of the round that you would like to update")
*/ val roundToEdit = askUserToChooseRound()
            if (roundToEdit != null) {
                // Display the current note details so you can decided what you want to change

           var option: Int
                do{option = roundAttributeMenu(roundToEdit)
                    when(option){
                        1 -> {var newTitle: String = ScannerInput.readNextLine("Please enter the new title")
                            roundAPI.updateRoundTitle(roundToEdit,newTitle)}
                        2 ->{ var newRoundId: Int = ScannerInput.readNextInt("Please enter the new round id")
                            roundAPI.updateRoundId(roundToEdit,newRoundId)}
                        3 -> { var newNoQuestionsAttempted: Int = ScannerInput.readNextInt("Please enter the new number of attempts")
                            roundAPI.updateQuestionsAttempted(roundToEdit,newNoQuestionsAttempted)}
                        4 -> updateQuestion()
                        99 -> updateRound()
                        0 -> println("exiting")


                        //Eventually put in update questions here
                    }}while(option!= 4 && option != 99 && option != 0)

                }


        }




fun roundAttributeMenu(roundToEdit:Rounds ):Int = ScannerInput.readNextInt(
    """
        Please Choose the attribute you would like to update
        1.Title: ${roundToEdit.roundTitle}
        2. RoundId: ${roundToEdit.roundId}
        3. Number of Attempts: ${roundToEdit.questionsAttempted}
        4. Questions: ${roundToEdit.questions}
        99. Choose a different Round to update
        0. Exit to main menu
    """.trimIndent()
)


fun deleteRound(){
    logger.info{"deleteRound() function invoked"}

        println(roundAPI.listAllRounds())
        if (roundAPI.numberOfRounds() > 0) {
            val idToDelete = ScannerInput.readNextInt("Please select the id of the round to delete")
            val roundToDelete = roundAPI.deleteRound(idToDelete)
            if (roundToDelete != null) {
                println("Delete Successful! Deleted round: ${roundToDelete.roundTitle}")
            } else {
                println("Delete NOT successful")
            }
        }


}

fun saveRound(){
    try {
        roundAPI.store()
        println("Rounds saved Successfuly")
    }catch(e: Exception){"Error writing fo file: $e"
    }
}
/**
 * Loads the notes data from the storage medium. It is used to retrieve previously saved notes.
 */
fun loadRound() {
    try {
        roundAPI.load()
        println("Rounds loaded successfully")
    }catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}