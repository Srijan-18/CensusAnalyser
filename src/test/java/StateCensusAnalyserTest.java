import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    private static final String STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

    @Test
    public void givenFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
                StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
                int numOfRecords = stateCensusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
                Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            int numOfRecords = stateCensusAnalyser.loadStateCensusData("./src/main/resources/IndiaStateCensusData.csv");
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }
}
