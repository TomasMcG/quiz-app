package controllers
import models.Rounds
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

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
            val newRound = Rounds(5, "newRound", 4)
            assertEquals(4, populatedRounds!!.numberOfRounds())
            assertTrue(populatedRounds!!.add(newRound))
            assertEquals(5, populatedRounds!!.numberOfRounds())

            //new rounds has latest id so it is the numberOfRounds. could also
            assertEquals(newRound, populatedRounds!!.findRounds(5))

            assertEquals(0, emptyRounds!!.numberOfRounds())
            assertTrue(emptyRounds!!.add(newRound))
            assertEquals(1, emptyRounds!!.numberOfRounds())
            assertEquals(newRound, emptyRounds!!.findRounds(emptyRounds!!.numberOfRounds() ))

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

        @Test
        fun `listCompleted Rounds`() {
            assertEquals(0, emptyRounds!!.numberOfRounds())
            assertTrue(emptyRounds!!.listAllRounds().lowercase().contains("no rounds"))
        }
        @Test
        fun `list incomplete Rounds`() {
            assertEquals(4, populatedRounds!!.numberOfRounds())
            val roundsString = populatedRounds!!.listIncompleteRounds().lowercase()
            assertTrue(roundsString.contains("geography"))
            assertTrue(roundsString.contains("history"))
            assertTrue(roundsString.contains("television"))
            assertTrue(roundsString.contains("video"))
        }
    }

@Nested
inner class numberOfRounds{
    @Test
    fun numberOfRounds(){
        assertEquals(4, populatedRounds!!.numberOfRounds())
        assertEquals(0, emptyRounds!!.numberOfRounds())
    }

    @Test
    fun numberOfRoundsCompleted(){
        assertEquals(4, populatedRounds!!.numberOfRounds())
        assertEquals(0, populatedRounds!!.numberOfCompletedRounds())
    }

    @Test
    fun numberOfRoundsIncomplete(){
        assertEquals(4, populatedRounds!!.numberOfIncompleteRounds())
        assertEquals(0, emptyRounds!!.numberOfIncompleteRounds())
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

        @Test
        fun `setting a round to complete and incomplete`(){

            assertTrue(geographyRound?.isCompleted == false)
            populatedRounds!!.setRoundToComplete(geographyRound!!)
            assertTrue(geographyRound?.isCompleted == true)

            populatedRounds!!.setRoundToIncomplete(geographyRound!!)
            assertTrue(geographyRound?.isCompleted == false)
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
    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`()
        {
            //save empty Rounds.xml file
            val storingRounds = RoundAPI(XMLSerializer(File("roundsTest.xml")))
            storingRounds.store()

            //loading empty Rounds.xml file into new object
            val loadedRounds = RoundAPI(XMLSerializer(File("roundsTest.xml")))
            loadedRounds.load()

            //comaring source of Rounds storingRounds with the xml loaded Rounds loadedRounds

            assertEquals(0,storingRounds.numberOfRounds())
            assertEquals(0,loadedRounds.numberOfRounds())
            assertEquals(storingRounds.numberOfRounds(),loadedRounds.numberOfRounds())

        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`()
        {
            // Storing 3 Rounds to the Rounds.XML file.
            val storingRounds = RoundAPI(XMLSerializer(File("roundsTest.xml")))
            storingRounds.add(geographyRound!!)
            storingRounds.add(televisionRound!!)
            storingRounds.add(videoGameRound!!)
            storingRounds.store()

            //Loading Rounds.xml into a different collection
            val loadedRounds = RoundAPI(XMLSerializer(File("roundsTest.xml")))
            loadedRounds.load()

            //Comparing the source of the Rounds (storingRounds) with the XML loaded Rounds (loadedRounds)
            assertEquals(3, storingRounds.numberOfRounds())
            assertEquals(3, loadedRounds.numberOfRounds())
            assertEquals(storingRounds.numberOfRounds(), loadedRounds.numberOfRounds())
            assertEquals(storingRounds.findRounds(0), loadedRounds.findRounds(0))
            assertEquals(storingRounds.findRounds(1), loadedRounds.findRounds(1))
            assertEquals(storingRounds.findRounds(2), loadedRounds.findRounds(2))

        }



}
}

