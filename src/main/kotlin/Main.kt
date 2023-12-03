import controllers.RoundAPI
import models.Questions
import models.Rounds
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import java.io.File
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}
private val roundAPI = RoundAPI(XMLSerializer(copyResourceToFile("rounds.xml")))

// this is where the xml file is location to be loaded and saved from is determined, instance of file object created.
// for rounds.xml files in resources.
// Get the XML file as an InputStream using the classpath, allows it to load from the jar when built
// code made by chatGPT
fun copyResourceToFile(fileName: String): File {
    val classLoader = XMLSerializer::class.java.classLoader
    val inputStream = requireNotNull(classLoader.getResourceAsStream(fileName)) {
        "Unable to load resource: $fileName"
    }

    val tempFile = File.createTempFile(fileName, null)

    inputStream.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return tempFile
}

// var roundAPI = RoundAPI(XMLSerializer(File("xmlFiles/rounds.xml")))

/**
 * The main function to run the application.
 *
 * This function initializes the necessary components, loads rounds, and starts the main menu.
 *
 *
 */
fun main() {
    loadRound()
    runMenu()
}

/**
 * Displays the main menu options and prompts the user to choose an option.
 *
 * @return The selected option.
 */
fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
         > ----------------------------------
         > |         Main Menu        
         > ----------------------------------
         > |   1) Run player Menu
         > |   2) Run Round and Questions Menu                        
         > |   3) Try the Quiz                         
         > ----------------------------------         
         > |   0) Exit                      
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

/**
 * Executes the chosen action based on the main menu option selected by the user.
 */
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> runRoundMenu()
            2 -> tryQuiz()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Displays the Round and Questions CRUD menu options and prompts the user to choose an option.
 *
 * @return The selected option.
 */
fun roundMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
         > ----------------------------------
         > |Round and Questions CRUD Menu         
         > ----------------------------------  
         >   Round Menu
         > |   1) Add a Round                
         > |   2) List all Rounds            
         > |   3) Update a Round             
         > |   4) Delete a Round      
         > ----------------------------------
         > | Questions MENU                      
         > |   5) Add a Question                
         > |   6) List all Questions            
         > |   7) Update a Question             
         > |   8) Delete a Question             
         > ----------------------------------
         > | Save and Load Menu
         > |   9) Save Rounds
         > |   10) Load Rounds
         > ----------------------------------         
         > |   0) Exit   
         >    -1)Back to Main Menu               
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

/**
 * Executes the chosen action based on the Round and Questions CRUD menu option selected by the user.
 */
fun runRoundMenu() {
    do {
        val option = roundMenu()
        when (option) {
            1 -> addRound()
            2 -> listRounds()
            3 -> updateRound()
            4 -> deleteRound()
            5 -> addQuestionToRound()
            6 -> listQuestions()
            7 -> updateQuestion()
            8 -> deleteQuestion()
            9 -> saveRound()
            10 -> loadRound()
            0 -> exitApp()
            -1 -> println("Going back to main menu")
            else -> println("Invalid option entered: $option")
        }
    } while (option != 0 && option != -1)
}

// -------------------------------------------------------------------
// Questions functions
/**
 * Adds a new question to a selected round.
 *
 * Prompts the user to choose a round and then enter details for a new question, including question text,
 * correct answer, difficulty, and possible answers. The user can add multiple possible answers.
 */
private fun addQuestionToRound() {
    val round: Rounds? = askUserToChooseRound()
    if (round != null) {
        val questionText = ScannerInput.readNextLine("\t Type the Question you want to ask: ")
        val correctAnswer = ScannerInput.readNextLine("\t Correct Answer: ")
        val difficulty = ScannerInput.readNextLine("\t Select Difficulty (Easy, Medium, Hard): ")
        val possibleAnswers = mutableListOf<String>()
        var moreAnswers: Int
        do {
            val newPossibleAnswer = ScannerInput.readNextLine("Enter a new possible answer")
            possibleAnswers.add(newPossibleAnswer)
            println("Possible answers so far")
            possibleAnswers.forEach { println(it) }
            moreAnswers = ScannerInput.readNextInt(
                """
                Do you want to enter another possible value:
                1. Yes 
                2. No
                """.trimIndent()
            )
        } while (moreAnswers == 1)

        if (round.addQuestion(Questions(1, questionText, possibleAnswers, correctAnswer, difficulty))) {
            println("Add Successful!")
        } else {
            println("Add NOT Successful")
        }
    }
}

/**
 * Asks the user to choose a round from the available rounds.
 *
 * Displays a list of available rounds and prompts the user to enter the id of the round they want to choose.
 *
 * @return The selected round or null if no valid round is chosen.
 */
private fun askUserToChooseRound(): Rounds? {
    println(roundAPI.listAllRounds())
    if (roundAPI.numberOfRounds() > 0) {
        val round = roundAPI.findRounds(ScannerInput.readNextInt("\nEnter the id of the round you want to deal with: "))
        if (round != null) {
            return round
        } else {
            println("Round id is not valid")
        }
    }
    return null
}

/**
 * Asks the user to choose a round by its title from the available rounds.
 *
 * Displays a list of available rounds and prompts the user to enter the id of the round they want to choose.
 *
 * @return The selected round or null if no valid round is chosen.
 */
private fun askUserToChooseRoundByTitle(): Rounds? {
    println(roundAPI.listAllRounds())
    if (roundAPI.numberOfRounds() > 0) {
        val round = roundAPI.findRounds(ScannerInput.readNextInt("\nEnter the id of the round you want to play: "))
        if (round != null) {
            return round
        } else {
            println("Round id is not valid")
        }
    }
    return null
}

/**
 * Deletes a selected question from a selected round.
 *
 * Prompts the user to choose a round and then choose a question from that round to delete.
 */
fun deleteQuestion() {
    logger.info { "deleteQuestion() function invoked" }

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

/**
 * Asks the user to choose a question from a selected round.
 *
 * Displays a list of questions associated with the chosen round and prompts the user to enter the id
 * of the question they want to choose.
 *
 * @param round The selected round to choose a question from.
 * @return The selected question or null if no valid question is chosen.
 */
private fun askUserToChooseQuestion(round: Rounds): Questions? {
    return if (round.numberOfQuestions() > 0) {
        print(round.listAllQuestions())
        round.findQuestion(ScannerInput.readNextInt("\nEnter the id of the Question to deal with: "))
    } else {
        println("No questions for chosen Round")
        null
    }
}

/**
 * Displays a list of questions associated with the selected round.
 *
 * Invokes the `listAllQuestions` function for the selected round and prints the details of each question.
 */
fun listQuestions() {
    logger.info { "listQuestions() function invoked" }
    val round = askUserToChooseRound()
    if (round != null) {
        val questions: ArrayList<Questions?> = round.questions
        if (questions.isNotEmpty()) {
            println(round.listAllQuestions())
        }
    }
}


/**
 * Updates a selected question associated with a selected round.
 *
 * Invokes the `updateQuestionText`, `updateQuestionCorrectAnswer`, or `updateQuestionId` function based on the user's choice.
 */
fun updateQuestion() {
    logger.info { "updateQuestion() function invoked" }
    val round: Rounds? = askUserToChooseRound()
    if (round != null) {
        val questionToEdit: Questions? = askUserToChooseQuestion(round)
        if (questionToEdit != null) {
            var option: Int
            do {
                option = questionAttributeMenu(questionToEdit)
                when (option) {
                    1 -> {
                        val newQuestionText: String =
                            ScannerInput.readNextLine("Please enter the new question text")
                        round.updateQuestionText(questionToEdit, newQuestionText)
                    }
                    2 -> {
                        val newCorrectAnswer: String =
                            ScannerInput.readNextLine("Please enter the new correct answer")
                        round.updateQuestionCorrectAnswer(questionToEdit, newCorrectAnswer)
                    }
                    3 -> {
                        val newQuestionId: Int =
                            ScannerInput.readNextInt("Please enter the new question id")
                        round.updateQuestionId(questionToEdit, newQuestionId)
                    }
                    99 -> updateQuestion()
                    100 -> updateRound()
                    0 -> println("Exiting")
                }
            } while (option != 99 && option != 100 && option != 0)
        }
    }
}

/**
 * Displays a menu for choosing a question attribute to update.
 *
 * Prompts the user to choose which attribute of the question to update and returns the selected option.
 *
 * @param questionToEdit The question being edited.
 * @return The selected option.
 */
fun questionAttributeMenu(questionToEdit: Questions): Int = ScannerInput.readNextInt(
    """
        Please Choose the attribute you would like to update
        1. Question text: ${questionToEdit.questionText}
        2. Question Correct Answer: ${questionToEdit.correctAnswer}
        3. Question id: ${questionToEdit.questionId}
        99. Choose a different Question to update
        100. Choose a different round to update
        0. Exit to main menu
    """.trimIndent()
)

/**
 * Adds a new round to the application.
 *
 * Prompts the user to enter a title for the round and then adds the round using the `add` function in `RoundAPI`.
 */
fun addRound() {
    logger.info { "addRound() function invoked" }
    val roundTitle = ScannerInput.readNextLine("Enter a title for the round: ")
    val isAdded = roundAPI.add(Rounds(roundTitle = roundTitle))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

/**
 * Lists rounds based on user choice.
 *
 * Invokes the `listAllRounds`, `listCompletedRounds`, or `listIncompleteRounds` function based on the user's choice.
 */
fun listRounds() {
    if (roundAPI.numberOfRounds() > 0) {
        val option = ScannerInput.readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Rounds          
                  > |   2) View Completed Rounds      
                  > |   3) View Incomplete Rounds 
                  >     4) View Easy Rounds
                  >     5) View Medium Rounds
                  >     6) View Hard Rounds
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> println(roundAPI.listAllRounds())
            2 -> println(roundAPI.listCompletedRounds())
            3 -> println(roundAPI.listIncompleteRounds())
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

/**
 * Updates the details of a selected round.
 *
 * Displays a menu for choosing the round attribute to update, such as title, roundId, number of attempts, completion status,
 * and questions. Invokes the corresponding `update` function in `RoundAPI` based on the user's choice.
 *
 *
 */
fun updateRound() {
    logger.info { "updateRound() function invoked" }
    val roundToEdit = askUserToChooseRound()
    if (roundToEdit != null) {
        var option: Int
        do {
            option = roundAttributeMenu(roundToEdit)
            when (option) {
                1 -> {
                    val newTitle: String = ScannerInput.readNextLine("Please enter the new title")
                    roundAPI.updateRoundTitle(roundToEdit, newTitle)
                }
                2 -> {
                    val newRoundId: Int = ScannerInput.readNextInt("Please enter the new round id")
                    roundAPI.updateRoundId(roundToEdit, newRoundId)
                }
                3 -> {
                    val newNoQuestionsAttempted: Int =
                        ScannerInput.readNextInt("Please enter the new number of attempts")
                    roundAPI.updateQuestionsAttempted(roundToEdit, newNoQuestionsAttempted)
                }
                4 -> setRoundCompletionStatus(roundToEdit)
                5 -> updateQuestion()
                99 -> updateRound()
                0 -> println("Exiting")
            }
        } while (option != 4 && option != 99 && option != 0)
    }
}

/**
 * Displays a menu for choosing a round attribute to update.
 *
 * Prompts the user to choose which attribute of the round to update and returns the selected option.
 *
 * @param roundToEdit The round being edited.
 * @return The selected option.
 */
fun roundAttributeMenu(roundToEdit: Rounds): Int = ScannerInput.readNextInt(
    """
        Please Choose the attribute you would like to update
        1. Title: ${roundToEdit.roundTitle}
        2. RoundId: ${roundToEdit.roundId}
        3. Number of Attempts: ${roundToEdit.questionsAttempted}
        4. Round Completion Status: ${roundToEdit.isCompleted}
        5. Questions: ${roundToEdit.questions}
        99. Choose a different Round to update
        0. Exit to main menu
    """.trimIndent()
)

/**
 * Deletes a round from the application.
 *
 * Displays a list of all rounds, prompts the user to select a round to delete, and deletes the selected round using
 * the `deleteRound` function in `RoundAPI`.
 */
fun deleteRound() {
    logger.info { "deleteRound() function invoked" }
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

/**
 * Saves the rounds data to the storage medium.
 *
 * Invokes the `store` function in `RoundAPI` to save the rounds to the specified file.
 */
fun saveRound() {
    try {
        roundAPI.store()
        println("Rounds saved successfully")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads the rounds data from the storage medium.
 *
 * Invokes the `load` function in `RoundAPI` to load the previously saved rounds from the specified file.
 */
fun loadRound() {
    try {
        roundAPI.load()
        println("Rounds loaded successfully")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Sets the completion status of a round to complete or incomplete based on user choice.
 *
 * Prompts the user to choose whether to set the round to complete or incomplete and updates the `isCompleted` property
 * accordingly using the `setRoundToComplete` or `setRoundToIncomplete` function in `RoundAPI`.
 *
 * @param roundToEdit The round being edited.
 */
fun setRoundCompletionStatus(roundToEdit: Rounds) {
    val completionStatusMessage = if (roundToEdit.isCompleted) "incomplete" else "complete"
    val choice = ScannerInput.readNextInt(
        """
            The round is currently $completionStatusMessage. Do you want to set it to ${if (roundToEdit.isCompleted) "incomplete" else "complete"}?
            1. Yes
            2. No
        """.trimIndent()
    )
    when (choice) {
        1 -> if (roundToEdit.isCompleted) roundAPI.setRoundToIncomplete(roundToEdit)
        2 -> println("The round status is $completionStatusMessage")
    }
}

/**
 * Attempts the quiz by prompting the user to choose a round and answering the questions in that round.
 *
 * Displays a welcome message, prompts the user to choose a round, and then iterates through the questions in the chosen
 * round. The user is asked to provide answers to the questions, and the results are displayed at the end.
 * The completion status of the round is updated if all answers are correct.
 */
fun tryQuiz() {
    println(
        """
    Welcome to the quiz
    --------------------------------------------------------------------------------------
    Our quiz has multiple rounds based on different things such as History or Geography
    To get started on the quiz please choose a round to try out
    --------------------------------------------------------------------------------------
        """.trimIndent()
    )
    val chosenRound = askUserToChooseRoundByTitle()
    if (chosenRound != null && chosenRound.questions.isNotEmpty()) {
        println(
            """
        You have chosen the ${chosenRound.roundTitle} Round
            """.trimIndent()
        )
        chosenRound.questionsAttempted++
        var numberOfCorrectAnswers = 0
        val numberOfQuestions = chosenRound.numberOfQuestions()
        // first display question 1,
        var index = 0
        while (index < numberOfQuestions) {
            println(chosenRound.questions[index]!!.questionText)
            println(chosenRound.questions[index]!!.possibleAnswers)
            val userAnswer: String = ScannerInput.readNextLine("Please Enter the Correct Answer")

            if (userAnswer == chosenRound.questions[index]!!.correctAnswer) {
                numberOfCorrectAnswers++
            }
            index++
        }
        println("Congratulation. You finished the quiz. You got $numberOfCorrectAnswers correct answers and ${numberOfQuestions - numberOfCorrectAnswers} wrong answers")
        if (numberOfCorrectAnswers == numberOfQuestions) {
            chosenRound.isCompleted = true
            println("Congratulations. You beat ${chosenRound.roundTitle} and got everything correct in ${chosenRound.questionsAttempted} attempts.")
        }
        println("Goodbye")
    } else if (chosenRound == null) {
        println("That round does not exist")
    } else if (chosenRound.questions.isEmpty()) {
        println("The chosen round has no questions")
    }
}

/**
 * Exits the application.
 */
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}
