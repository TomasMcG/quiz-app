package models
import controllers.RoundAPI
import org.junit.jupiter.api.*
import persistence.XMLSerializer
import java.io.File

class RoundsQuestionsAPITest {


    private var geographyRound: Rounds? = null
    private var historyRound: Rounds? = null
    private var televisionRound: Rounds? = null
    private var videoGameRound: Rounds? = null

    private var geographyQuestion1: Questions? = null
    private var geographyQuestion2: Questions? = null
    private var geographyQuestion3: Questions? = null
    private var geographyQuestion4: Questions? = null

    private var populatedRounds: RoundAPI? = RoundAPI(XMLSerializer(File("rounds.xml")))
    private var emptyRounds: RoundAPI? = RoundAPI(XMLSerializer(File("rounds.xml")))

    //private var geographyQuestions: ArrayList<Questions> = emptyArrayList()
    private var geographyQuestions: ArrayList<Questions?> = arrayListOf(geographyQuestion1,geographyQuestion2,geographyQuestion3,geographyQuestion4)





    @BeforeEach
    fun setup() {
        geographyRound= Rounds( 1,"geographyRound",4,geographyQuestions)
        historyRound = Rounds(2 , "historyRound" )
        televisionRound = Rounds(3, "televisionRound")
        videoGameRound = Rounds(4, "videoGameRound" , 0)

        geographyQuestion1 = Questions(1,
            """Which of the following counties is coastal:
            |1.Waterford
            |2.Tipperary
            |3.Limerick
        """.trimMargin(),"Waterford")
        geographyQuestion2 = Questions(2,
            """What city is the capital of Ireland:
            |1.Dublin city
            |2.Cork city
            |3.Waterford City
        """.trimMargin(),"Dublin city")
        geographyQuestion3 = Questions(3,
            """What county in Ireland has the smallest population:
            |1.Sligo
            |2.Leitrim
            |3.Roscommon
        """.trimMargin(),"Leitrim")
        geographyQuestion4 = Questions(4,
            """What is the population of Ireland(to the nearest million):
            |1.5 million
            |2.4 million
            |3.6 million
        """.trimMargin(),"5 million")


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
        fun `Rounds contains question values when created with them`() {


        }
    }
}
