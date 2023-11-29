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


        //adding 5 Note to the notes api
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










}

