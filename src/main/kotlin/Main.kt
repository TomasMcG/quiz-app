import models.Questions
import models.Rounds
import mu.KotlinLogging

import utils.ScannerInput
import java.lang.System.exit
import java.time.LocalDate

private val logger = KotlinLogging.logger {}
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
         > |   9) Add a Round                
         > |   10) List all Rounds            
         > |   11) Update a Round             
         > |   12) Delete a Round             
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
            //5 -> addQuestionToRound()
            6 -> listQuestions()
            7 -> updateQuestion()
            8 -> deleteQuestion()
            9 -> addRound()
            10 -> listRounds()
            11 -> updateRound()
            12  -> deleteRound()

            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    }while(true)

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
/*private fun addQuestionToRound() {
    val round: Rounds? = askUserToChooseRound()
    if (round != null) {
        if (round.addQuestion(Questions(1,
                questionText = ScannerInput.readNextLine("\t Possible Questions: "),
                correctAnswer = ScannerInput.readNextLine("\t Possible Answers: ")

            )))

            println("Add Successful!")
        else println("Add NOT Successful")
    }
}*/


/*private fun askUserToChooseRound(): Rounds? {
    listRounds()
return Rounds(1,2,)}
    if (roundAPI.numberOfrounds() > 0) {
        val round = roundAPI.findRound(ScannerInput.readNextInt("\nEnter the id of the round: "))
        if (round != null) {
            return round
        } else {
            println("Round id is not valid")
        }
    }
    return null //selected note is not active
}*/



fun listQuestions(){
    logger.info{"listQuestions() function invoked"}
}

fun updateQuestion(){
    logger.info{"updateQuestion() function invoked"}
}

fun deleteQuestion(){
    logger.info{"deleteQuestion() function invoked"}
}
//------------------------------------------------------------------------------------
fun addRound(){
    logger.info{"addRound() function invoked"}
    //these info functions are lambdas
    val noteTitle = ScannerInput.readNextLine("Enter a title for the note: ")
    val notePriority = ScannerInput.readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = ScannerInput.readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle = noteTitle, notePriority = notePriority, noteCategory = noteCategory))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}



fun listRounds(){
    logger.info{"listRounds() function invoked"}
}

fun updateRound(){
    logger.info{"updateRound() function invoked"}
}

fun deleteRound(){
    logger.info{"deleteRound() function invoked"}
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}