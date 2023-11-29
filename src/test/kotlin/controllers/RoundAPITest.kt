package controllers
import models.Rounds
import models.Questions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import persistence.YAMLSerializer
import java.io.File
import java.time.LocalDate

class RoundAPITest {

    private var geographyRound: Rounds? = null
    private var historyRound: Rounds? = null
    private var televisionRound: Rounds? = null
    private var videoGameRound: Rounds? = null

    private var populatedRounds: RoundAPI? = RoundAPI(XMLSerializer(File("rounds.xml")))
    private var emptyRounds: RoundAPI? = RoundAPI(XMLSerializer(File("rounds.xml")))


    @BeforeEach
    fun setup() {
        geographyRound= Rounds( 1,"geographyRound",4)
       historyRound = Rounds(2 , "historyRound" )
        televisionRound = Rounds(3, "televisionRound")
        videoGameRound = Rounds(4, "videoGameRound" , 0)
        println(geographyRound!!.roundId)

        //adding 5 round to the rounds api
        populatedRounds!!.add(geographyRound!!)
        populatedRounds!!.add(historyRound!!)
        populatedRounds!!.add(televisionRound!!)
        populatedRounds!!.add(videoGameRound!!)

    }

    @AfterEach
    fun tearDown() {
        geographyRound = null
        historyRound = null
        televisionRound = null
        videoGameRound = null
        populatedRounds = null
        emptyRounds = null


    }

    @Nested
    inner class AddRounds{
        @Test
        fun addingRoundsToPopulatedAndEmpty() {
            val newRound = Rounds(1, "newRound", 4)
            assertEquals(4, populatedRounds!!.numberOfRounds())
            assertTrue(populatedRounds!!.add(newRound))
            assertEquals(5, populatedRounds!!.numberOfRounds())
            assertEquals(newRound, populatedRounds!!.findRounds(populatedRounds!!.numberOfRounds() - 1))

            assertEquals(0, emptyRounds!!.numberOfRounds())
            assertTrue(emptyRounds!!.add(newRound))
            assertEquals(1, emptyRounds!!.numberOfRounds())
            assertEquals(newRound, emptyRounds!!.findRounds(emptyRounds!!.numberOfRounds() - 1))

        }
        }
    @Nested
    inner class ListRounds {

        @Test
        fun `listAllRounds returns No Rounds Stored message when ArrayList is empty`() {
            assertEquals(0, emptyRounds!!.numberOfRounds())
            assertTrue(emptyRounds!!.listAllRounds().lowercase().contains("no rounds"))
        }

        @Test
        fun `listAllRounds returns Rounds when ArrayList has Rounds stored`() {
            assertEquals(4, populatedRounds!!.numberOfRounds())
            val roundsString = populatedRounds!!.listAllRounds().lowercase()
            assertTrue(roundsString.contains("geography"))
            assertTrue(roundsString.contains("history"))
            assertTrue(roundsString.contains("television"))
            assertTrue(roundsString.contains("video"))


        }
    }

@Nested
inner class numberOfRounds{
    @Test
    fun numberOfRoundsCorrect(){
        assertEquals(4, populatedRounds!!.numberOfRounds())
        assertEquals(0, emptyRounds!!.numberOfRounds())
    }
}

    @Nested
    inner class updateRounds{




        @Test
        fun `updating a round that exists returns true and updates`(){

            assertTrue(geographyRound!!.roundTitle.contains("geographyRound"))
            populatedRounds!!.updateRoundTitle(geographyRound,"newGeog" )
            assertTrue(geographyRound!!.roundTitle.contains("newGeog"))

            //assertEquals(3,televisionRound!!.roundId )
            populatedRounds!!.updateRoundId( geographyRound,6 )
            assertEquals(6, geographyRound!!.roundId )

            assertEquals(4,geographyRound!!.questionsAttempted )
            populatedRounds!!.updateQuestionsAttempted( geographyRound,4)
            assertTrue(geographyRound!!.roundTitle.contains("newGeog"))




        }

    }


    @Nested
    inner class DeleteRounds{
        @Test
        fun `deleting a Round that does not exist, return null`(){
            assertNull(emptyRounds!!.deleteRound(0))
            assertNull(populatedRounds!!.deleteRound(-1))
            assertNull(populatedRounds!!.deleteRound(5))
        }

        @Test
        fun `deleting a Round that exists and returns deleted object`(){
            assertEquals(4,populatedRounds!!.numberOfRounds())
            assertEquals(geographyRound, populatedRounds!!.deleteRound(1))
            assertEquals(3,populatedRounds!!.numberOfRounds())
            assertEquals(historyRound, populatedRounds!!.deleteRound(2))
            assertEquals(2,populatedRounds!!.numberOfRounds())
        }


    }




}

