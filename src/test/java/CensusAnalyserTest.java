import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

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
            int numOfRecords = censusAnalyser.loadStateCensusData
                    ("./src/test/resources/IndianStateCensusWrongDelimiter.csv");
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
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                    "stateName", false),
                            CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabaticalOrder_ShouldReturnWestBengalAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                    "stateName", false),
                            CensusDAO[].class);
            Assert.assertEquals("West Bengal", censusDAOS[censusDAOS.length - 1].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnADAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                    "stateCode", false),
                            CensusDAO[].class);
            Assert.assertEquals("AD", censusDAOS[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnWBAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                    "stateCode", false),
                            CensusDAO[].class);
            Assert.assertEquals("WB", censusDAOS[censusDAOS.length - 1].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnUttarPradeshAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCensusDataPopulationWise.json",
                                        "population", true),
                             CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnBiharAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                                    sortDataToJSON(
                                    "./src/test/resources/IndiaStateCensusDataPopulationDensityWise.json",
                                    "densityPerSqKm", true),
                                    CensusDAO[].class);
            Assert.assertEquals("Bihar", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnRajasthanAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(STATE_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/IndiaStateCensusDataAreaWise.json",
                                    "areaInSqKm", true),
                            CensusDAO[].class);
            Assert.assertEquals("Rajasthan", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnCaliforniaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataPopulationWise.json",
                                    "population", true),
                            CensusDAO[].class);
            Assert.assertEquals("California", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void
    givenUSCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnDistrictOfColumbiaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataPopulationDensityWise.json",
                                    "populationDensity", true),
                            CensusDAO[].class);
            Assert.assertEquals("District of Columbia", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnAlaskaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataTotalAreaWise.json",
                                    "totalArea", true),
                            CensusDAO[].class);
            Assert.assertEquals("Alaska", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}