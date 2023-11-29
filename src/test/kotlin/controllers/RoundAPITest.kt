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
        geographyRound= Rounds( 1,"geographyRound")
       historyRound = Rounds(2 , "historyRound" )
        televisionRound = Rounds(3, "televisionRound")
        videoGameRound = Rounds(4, "videoGameRound" , 0)


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
        assertEquals(4, emptyRounds!!.numberOfRounds())
    }
}

    @Nested
    inner class updateRounds{
        @Test
        fun `updating a round that does not exist returns false`(){
            assertTrue(geographyRound!!.roundTitle!!.contains("geog"))
            populatedRounds!!.updateRoundTitle(geographyRound,"newGeog" )
            assertTrue(geographyRound!!.roundTitle!!.contains("newGeog"))
            assertFalse(populatedRounds!!.updateRoundTitle(6,Rounds("Updating Round",2,"work",false, LocalDate.of(2023, 11, 4),LocalDate.of(2023, 11, 6),"Round Content","ToDo")))

            assertFalse(populatedRounds!!.updateRoundId(-1,Rounds("Updating Round",2,"work",false,LocalDate.of(2023, 11, 4),LocalDate.of(2023, 11, 6),"Round Content","ToDo")))
            assertFalse(emptyRounds!!.updateQuestionsAttempted(0,Rounds("Updating Round",2,"work",false,LocalDate.of(2023, 11, 4),LocalDate.of(2023, 11, 6),"Round Content","ToDo")))
            assertFalse(emptyRounds!!.updateQuestionsAttempted(0,Rounds("Updating Round",2,"work",false,LocalDate.of(2023, 11, 4),LocalDate.of(2023, 11, 6),"Round Content","ToDo")))

        }



        @Test
        fun `updating a round that exists returns true and updates`(){
//check round 5 exists and check the contents
            assertEquals(swim, populatedRounds!!.findRounds(4))
            assertEquals("Swim - Pool",populatedRounds!!.findRounds(4)!!.roundTitle)
            assertEquals(3, populatedRounds!!.findRounds(4)!!.roundId)
            assertEquals("Hobby", populatedRounds!!.findRound(4)!!.question)




            //update Round 5 with new info and ensure contents updated successfully
            assertTrue(populatedRounds!!.updateRounds(4,Rounds("Updating Round",2,"College",false,LocalDate.of(2023, 11, 4),LocalDate.of(2023, 11, 6),"Round Content","ToDo")))
            assertEquals("Updating Round",populatedRounds!!.findRounds(4)!!.RoundTitle)
            assertEquals(2, populatedRounds!!.findRounds(4)!!.RoundPriority)
            assertEquals("College", populatedRounds!!.findRounds(4)!!.roundCategory)
            assertEquals(LocalDate.of(2023, 11, 6), populatedRounds!!.findRounds(4)!!.lastModified)
        }

    }







}

