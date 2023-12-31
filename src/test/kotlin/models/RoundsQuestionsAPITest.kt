package models
import controllers.RoundAPI
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoundsQuestionsAPITest {
    // singulgar Questions
    // array list of those questions object is geographyQuestionss
// geography round is my populated list that stores my arrays of Questions
    // run the Questions tests on the grography rounds array list or on it as an attribute of geography round

    // questions objects
    private var geographyQuestion1: Questions? = null
    private var geographyQuestion2: Questions? = null
    private var geographyQuestion3: Questions? = null
    private var geographyQuestion4: Questions? = null

    // Arraylists of questions objects
    // private var geographyQuestions: ArrayList<Questions?> = arrayListOf(geographyQuestion1, geographyQuestion2, geographyQuestion3, geographyQuestion4)

    // individual round objects
    private var geographyRound: Rounds? = null
    private var emptyQuestionsRound: Rounds? = null

    // array list of rounds
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
        val geographyQuestions: ArrayList<Questions?> = arrayListOf(geographyQuestion1, geographyQuestion2, geographyQuestion3, geographyQuestion4)
        geographyRound = Rounds(1, "geographyRound", 4, geographyQuestions)
        emptyQuestionsRound = Rounds(2, "emptyQuestionsRound,4")
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
    inner class AddRounds {
        @Test
        fun `Add questions to a round`() {
            assertEquals(4, geographyRound?.numberOfQuestions())
            val newQuestion = Questions(
                5,
                "New Question:",
                mutableListOf("AnswerOne", "AnswerTwo", "AnswerThree"),
                "5 million",
                "medium"
            )
            geographyRound?.addQuestion(newQuestion)
            geographyRound?.questions?.contains(newQuestion)

            //  logger.info{geographyRound?.questions}
            assertEquals(5, geographyRound?.questions?.size)
        }
    }

    @Nested
    inner class ListQuestions {
        @Test
        fun `listAllQuestions returns No Questions Stored message when ArrayList is empty`() {
            assertEquals(0, emptyQuestionsRound!!.numberOfQuestions())
            assertTrue(emptyQuestionsRound!!.listAllQuestions().lowercase().contains("no questions"))
        }

        @Test
        fun `listAllQuestions returns Questions when ArrayList has Questions stored`() {
            assertEquals(4, geographyRound!!.numberOfQuestions())
            val roundsString = geographyRound?.listAllQuestions()?.lowercase()
            if (roundsString != null) {
                assertTrue(roundsString.contains("waterford"))
                assertTrue(roundsString.contains("cork"))
                assertTrue(roundsString.contains("sligo"))
            }
        }
    }

    @Nested
    inner class DeleteQuestions {
        @Test
        fun `deleting a Question that does not exist, return null`() {
            assertFalse(geographyRound!!.deleteQuestions(0))
            assertFalse(geographyRound!!.deleteQuestions(-1))
            assertFalse(geographyRound!!.deleteQuestions(5))
        }

        @Test
        fun `deleting a Question that exists and returns deleted object`() {
            assertEquals(4, geographyRound!!.numberOfQuestions())
            assertEquals(true, geographyRound!!.deleteQuestions(1))
            assertEquals(3, geographyRound!!.numberOfQuestions())
        }
    }

    @Nested
    inner class UpdateQuestions {
        @Test
        fun `updating a question that exists returns true and updates`() {
            // update question text
            assertTrue(geographyRound!!.questions.contains(geographyQuestion1))
            assertTrue(geographyQuestion1!!.questionText.contains("Which of the following counties is coastal"))
            geographyRound!!.updateQuestionText(geographyQuestion1, "updated text")
            assertTrue(geographyQuestion1!!.questionText.contains("updated text"))

            // update question id
            assertTrue(geographyQuestion1!!.questionId == 1)
            geographyRound!!.updateQuestionId(geographyQuestion1, 10)
            assertTrue(geographyQuestion1!!.questionId == 10)

            // update question correct answer
            assertTrue(geographyQuestion1!!.correctAnswer.contains("Waterford"))
            geographyRound!!.updateQuestionCorrectAnswer(geographyQuestion1, "updated answer")
            assertTrue(geographyQuestion1!!.correctAnswer.contains("updated answer"))
        }
    }
}
