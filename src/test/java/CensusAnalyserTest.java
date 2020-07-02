import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.bridgelabz.censusanalyser.model.StateCodeCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCensusData(
                    "./src/main/resources/IndiaStateCensusData.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void giveFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfEntries = censusAnalyser.loadStateCensusData(
                    "./src/test/resources/IndiaStateCensusData.pdf");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCensusData("./src/test/resources/IndianStateCensusWrongDelimiter.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            CensusAnalyser stateCensusAnalyser = new CensusAnalyser();
            int numOfRecords = stateCensusAnalyser.loadStateCensusData
                    ("./src/test/resources/IndianStateCensusWithWrongHeader.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCodeData(
                    "./src/main/resources/IndiaStateCode.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void giveStateCodeFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfEntries = censusAnalyser.loadStateCodeData(
                    "./src/test/resources/IndiaStateCode.pdf");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadStateCodeData
                    ("./src/test/resources/IndianStateCodeWrongDelimiter.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            CensusAnalyser stateCensusAnalyser = new CensusAnalyser();
            int numOfRecords = stateCensusAnalyser.loadStateCodeData
                    ("./src/test/resources/IndianStateCodeWithWrongHeader.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabaticalOrder_ShouldReturnAndraPradeshAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            StateCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, StateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", stateCensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabaticalOrder_ShouldReturnWestBengalAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            StateCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, StateCensusCSV[].class);
            Assert.assertEquals("West Bengal", stateCensusCSV[stateCensusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnADAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateCodeWiseSortedStateCodeData();
            StateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, StateCodeCSV[].class);
            Assert.assertEquals("AD", stateCodeCSV[0].StateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnWBAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateCodeWiseSortedStateCodeData();
            StateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, StateCodeCSV[].class);
            Assert.assertEquals("WB", stateCodeCSV[stateCodeCSV.length - 1].StateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnTotalNumberOfStates() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationSortedCensusData();
            StateCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, StateCensusCSV[].class);
            Assert.assertEquals(29, stateCensusCSV.length);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnUttarPradeshAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationSortedCensusData();
            StateCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, StateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnBiharAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            StateCensusCSV[] stateCensusCSV = new Gson().fromJson(
                    censusAnalyser.getPopulationDensitySortedCensusData(),
                    StateCensusCSV[].class);
            Assert.assertEquals("Bihar", stateCensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}