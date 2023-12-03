package models
import controllers.RoundAPI
import mu.KotlinLogging
import org.junit.jupiter.api.*
import persistence.XMLSerializer
import utils.Utilities.emptyArrayList
import java.io.File
import kotlin.test.assertEquals

/**
 * Test class for [RoundsQuestionsAPI] functionalities.
 */
class RoundsQuestionsAPITest {
    //singulgar Questions
    //array list of those questions object is geographyQuestionss
//geography round is my populated list that stores my arrays of Questions
    //run the Questions tests on the grography rounds array list or on it as an attribute of geography round



    private val logger = KotlinLogging.logger {}

    //questions objects
    private var geographyQuestion1: Questions? = null
    private var geographyQuestion2: Questions? = null
    private var geographyQuestion3: Questions? = null
    private var geographyQuestion4: Questions? = null

    //Arraylists of questions objects
    private var geographyQuestions: ArrayList<Questions?> = arrayListOf(geographyQuestion1,geographyQuestion2,geographyQuestion3,geographyQuestion4)


    //individual round objects
    private var geographyRound: Rounds? = null
    private var emptyQuestionsRound: Rounds? = null



    //array list of rounds
    private var populatedRounds: RoundAPI? = RoundAPI(XMLSerializer(File("roundsQuestionsTests.xml")))
    private var emptyRounds: RoundAPI? = RoundAPI(XMLSerializer(File("roundsQuestionsTests.xml")))






    @BeforeEach
    fun setup() {


        geographyQuestion1 = Questions(
            1,
            "Which of the following counties is coastal:",
            mutableListOf("Waterford", "Tipperary", "Limerick"),
            "Waterford",
            "easy"
        )

        geographyQuestion2 = Questions(
            2,
            "What city is the capital of Ireland:",
            mutableListOf("Dublin city", "Cork city", "Waterford City"),
            "Dublin city",
            "medium"
        )

        geographyQuestion3 = Questions(
            3,
            "What county in Ireland has the smallest population:",
            mutableListOf("Sligo", "Leitrim", "Roscommon"),
            "Leitrim",
            "hard"
        )

        geographyQuestion4 = Questions(
            4,
            "What is the population of Ireland (to the nearest million):",
            mutableListOf("5 million", "4 million", "6 million"),
            "5 million",
            "easy"
        )
         var geographyQuestions: ArrayList<Questions?> = arrayListOf(geographyQuestion1,geographyQuestion2,geographyQuestion3,geographyQuestion4)
        geographyRound= Rounds( 1,"geographyRound",4,geographyQuestions)
        emptyQuestionsRound = Rounds(2,"emptyQuestionsRound,4")
        populatedRounds!!.add(geographyRound!!)


    }

    @AfterEach
    fun tearDown() {
        geographyRound = null

        populatedRounds = null
        emptyRounds = null
        geographyQuestion1 = null
        geographyQuestion2 = null
        geographyQuestion3 = null
        geographyQuestion4 = null

    }

    @Nested
    inner class AddRounds{
        @Test
        fun `Add questions to a round`() {
            assertEquals(4,geographyRound?.numberOfQuestions())
            var newQuestion: Questions = Questions(
                5,
                "New Question:",
                mutableListOf("AnswerOne", "AnswerTwo", "AnswerThree"),
                "5 million",
                "medium"
            )
            geographyRound?.addQuestion(newQuestion)
            geographyRound?.questions?.contains(newQuestion)

          //  logger.info{geographyRound?.questions}
            assertEquals(5,geographyRound?.questions?.size)
        }
    }

    @Nested
    inner class ListQuestions {
        @Test
        fun `listAllQuestions returns No Questions Stored message when ArrayList is empty`() {
            Assertions.assertEquals( 0,emptyQuestionsRound!!.numberOfQuestions())
            Assertions.assertTrue(emptyQuestionsRound!!.listAllQuestions().lowercase().contains("no questions"))
        }

        @Test
        fun `listAllQuestions returns Questions when ArrayList has Questions stored`() {
            Assertions.assertEquals(4, geographyRound!!.numberOfQuestions())
            val roundsString = geographyRound?.listAllQuestions()?.lowercase()
            if (roundsString != null) {
                Assertions.assertTrue(roundsString.contains("waterford"))
                Assertions.assertTrue(roundsString.contains("cork"))
                Assertions.assertTrue(roundsString.contains("sligo"))
            }
        }
    }

    @Nested
    inner class DeleteQuestions{
        @Test
        fun `deleting a Question that does not exist, return null`(){
            Assertions.assertFalse(geographyRound!!.deleteQuestions(0))
            Assertions.assertFalse(geographyRound!!.deleteQuestions(-1))
            Assertions.assertFalse(geographyRound!!.deleteQuestions(5))
        }

        @Test
        fun `deleting a Question that exists and returns deleted object`(){
            Assertions.assertEquals(4, geographyRound!!.numberOfQuestions())
            Assertions.assertEquals(true,geographyRound!!.deleteQuestions(1))
            Assertions.assertEquals(3, geographyRound!!.numberOfQuestions())
        }
    }

    @Nested
    inner class updateQuestions{
        @Test
        fun `updating a question that exists returns true and updates`(){
            //update question text
            Assertions.assertTrue(geographyRound!!.questions.contains(geographyQuestion1))
            Assertions.assertTrue(geographyQuestion1!!.questionText.contains("Which of the following counties is coastal"))
            geographyRound!!.updateQuestionText(geographyQuestion1,"updated text" )
            Assertions.assertTrue(geographyQuestion1!!.questionText.contains("updated text"))

            //update question id
            Assertions.assertTrue(geographyQuestion1!!.questionId == 1)
            geographyRound!!.updateQuestionId(geographyQuestion1,10 )
            Assertions.assertTrue(geographyQuestion1!!.questionId == 10)

            //update question correct answer
            Assertions.assertTrue(geographyQuestion1!!.correctAnswer.contains("Waterford"))
            geographyRound!!.updateQuestionCorrectAnswer(geographyQuestion1,"updated answer" )
            Assertions.assertTrue(geographyQuestion1!!.correctAnswer.contains("updated answer"))
        }
    }
}
