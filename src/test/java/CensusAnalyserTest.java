import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    private static final String STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndiaCensusFile_WhenProper_ShouldReturnCountOfEntries() {
        try {
            Assert.assertEquals(29, new CensusAnalyser()
                                .loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class));
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData(
                    "./src/main/resources/IndiaStateCensusData.csv", IndiaStateCensusCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void giveFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            new CensusAnalyser().loadCensusData(
                    "./src/test/resources/IndiaStateCensusData.pdf", IndiaStateCensusCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/IndianStateCensusWrongDelimiter.csv",
                                                IndiaStateCensusCSV.class);
          } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/IndianStateCensusWithWrongHeader.csv",
                                                IndiaStateCensusCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }

    }

    @Test
    public void givenStateCodeFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {

            Assert.assertEquals(29, new CensusAnalyser().loadCensusData(STATE_CODE_CSV_FILE_PATH,
                                                                                IndiaStateCodeCSV.class));
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData("./src/main/resources/IndiaStateCode.csv",
                    IndiaStateCodeCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/IndianStateCodeWrongDelimiter.csv",
                                               IndiaStateCodeCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/IndianStateCodeWithWrongHeader.csv",
                                                IndiaStateCodeCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabeticalOrder_ShouldReturnAndraPradeshAsFirstElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                "stateName", false),
                CensusDAO[].class);
        Assert.assertEquals("Andhra Pradesh", censusDAOS[0].stateName);
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabeticalOrder_ShouldReturnWestBengalAsLastElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                "stateName", false),
                CensusDAO[].class);
        Assert.assertEquals("West Bengal", censusDAOS[censusDAOS.length - 1].stateName);
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnADAsFirstElement()
            throws CensusAnalyserException, NoSuchFieldException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CODE_CSV_FILE_PATH, IndiaStateCodeCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                "stateCode", false),
                CensusDAO[].class);
        Assert.assertEquals("AP", censusDAOS[0].stateCode);
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnWBAsLastElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CODE_CSV_FILE_PATH, IndiaStateCodeCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                "stateCode", false),
                CensusDAO[].class);
        Assert.assertEquals("WB", censusDAOS[censusDAOS.length - 1].stateCode);
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnUttarPradeshAsFirstElement()
            throws CensusAnalyserException, NoSuchFieldException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCensusDataPopulationWise.json",
                                "population", true),
                CensusDAO[].class);
        Assert.assertEquals("Uttar Pradesh", censusDAOS[0].stateName);
    }

    @Test
    public void givenStateCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnBiharAsFirstElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON(
                                "./src/test/resources/IndiaStateCensusDataPopulationDensityWise.json",
                                "populationDensity", true),
                CensusDAO[].class);
        Assert.assertEquals("Bihar", censusDAOS[0].stateName);
    }

    @Test
    public void givenStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnRajasthanAsFirstElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(STATE_CENSUS_CSV_FILE_PATH, IndiaStateCensusCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/IndiaStateCensusDataAreaWise.json",
                                "totalArea", true),
                CensusDAO[].class);
        Assert.assertEquals("Rajasthan", censusDAOS[0].stateName);
    }

    @Test
    public void givenUSCensusFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
            int numOfRecords = new CensusAnalyser().loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData("./src/main/resources/USCensusDataWithWrongHeader.csv",
                                                USCensusDataCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/USCensusData.pdf",
                    USCensusDataCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/USCensusDataWithWrongDelimiter.csv",
                    USCensusDataCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData("./src/test/resources/USCensusDataWithWrongHeader.csv",
                    USCensusDataCSV.class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }


    @Test
    public void givenUSCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnCaliforniaAsFirstElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/USCensusDataPopulationWise.json",
                                "population", true),
                CensusDAO[].class);
        Assert.assertEquals("California", censusDAOS[0].stateName);
    }

    @Test
    public void
    givenUSCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnDistrictOfColumbiaAsFirstElement()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
        CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                        sortDataToJSON("./src/test/resources/USCensusDataPopulationDensityWise.json",
                                "populationDensity", true),
                CensusDAO[].class);
        Assert.assertEquals("District of Columbia", censusDAOS[0].stateName);
    }

    @Test
    public void givenUSStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnAlaskaAsFirstElement()
            throws NoSuchFieldException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataTotalAreaWise.json",
                                    "totalArea", true),
                    CensusDAO[].class);
            Assert.assertEquals("Alaska", censusDAOS[0].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenInvalidFiledName_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataTotalAreaWise.json",
                                    "total", true),
                    CensusDAO[].class);
        } catch (Exception e) {
            Assert.assertEquals("INVALID FIELD",e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenInvalidFiledNameAndInAscendingOrder_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, USCensusDataCSV.class);
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataTotalAreaWise.json",
                                    "total", false),
                    CensusDAO[].class);
        } catch (Exception e) {
            Assert.assertEquals("INVALID FIELD",e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenNotLoadedBeforeSorting_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            CensusDAO[] censusDAOS = new Gson().fromJson(censusAnalyser.
                            sortDataToJSON("./src/test/resources/USCensusDataTotalAreaWise.json",
                                    "total", false),
                    CensusDAO[].class);
        } catch (Exception e) {
            Assert.assertEquals("NO ELEMENTS IN LIST TO SORT",e.getMessage());
        }
    }

    @Test
    public void givenUSAndIndiaCensusFile_ShouldReturnDataOfMostAsMostDenselyPopulousState()
            throws NoSuchFieldException, CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        CensusDAO mostDenseStateData = censusAnalyser.
                getMostDenseState(US_CENSUS_CSV_FILE_PATH,STATE_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals("District of Columbia", mostDenseStateData.stateName);
    }
}