import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.bridgelabz.censusanalyser.util.Country;
import com.bridgelabz.censusanalyser.util.SortData;
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
                                .loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH));
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA,
                                        "./src/main/resources/IndiaStateCensusData.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void giveFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA,
                    "./src/test/resources/IndiaStateCensusData.pdf");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA,
                                        "./src/test/resources/IndianStateCensusWrongDelimiter.csv");
          } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA,
                    "./src/test/resources/IndianStateCensusWithWrongHeader.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }

    }

    @Test
    public void givenStateCodeFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {

            Assert.assertEquals(29, new CensusAnalyser().loadCensusData(Country.INDIA,
                                STATE_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV_FILE_PATH));
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH,
                                                "./src/main/resources/IndiaStateCode.csv"
            );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH,
                                                 "./src/test/resources/IndianStateCodeWrongDelimiter.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH,
                                                "./src/test/resources/IndianStateCodeWithWrongHeader.csv");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenStateCensusFile_WhenSortedInAlphabeticalOrder_ShouldReturnAndraPradeshAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.STATE_NAME,
                                    "./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                    false),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenStateCensusFile_WhenSortedInReverseAlphabeticalOrder_ShouldReturnWestBengalAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.STATE_NAME,
                                    "./src/test/resources/IndiaStateCensusDataStateNameWise.json",
                                    true),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedAccordingToStateCode_ShouldReturnAPAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH,
                                            STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA_STATE_CODE, SortData.SortAccordingTo.STATE_CODE,
                                    "./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                    false),
                            IndiaStateCodeCSV[].class);
            Assert.assertEquals("AP", censusDataCSVS[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenSortedinReverseAccordingToStateCode_ShouldReturnWBAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH,
                    STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA_STATE_CODE, SortData.SortAccordingTo.STATE_CODE,
                                    "./src/test/resources/IndiaStateCodeDataStateCodeWise.json",
                                    true),
                    IndiaStateCodeCSV[].class);
            Assert.assertEquals("WB", censusDataCSVS[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnUttarPradeshAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.POPULATION,
                                    "./src/test/resources/IndiaStateCensusDataPopulationWise.json",
                                    true ),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationSortedInAscendingOrder_ShouldReturnUttarPradeshAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.POPULATION,
                                    "./src/test/resources/IndiaStateCensusDataPopulationWise.json",
                                    false),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusDataCSVS[censusDataCSVS.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnBiharAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.POPULATION_DENSITY,
                                    "./src/test/resources/IndiaStateCensusDataPopulationDensityWise.json",
                                    true),
                            IndiaStateCensusCSV[].class);
            Assert.assertEquals("Bihar", censusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenPopulationDensitySortedInAscendingOrder_ShouldReturnBiharAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.POPULATION_DENSITY,
                                    "./src/test/resources/IndiaStateCensusDataPopulationDensityWise.json",
                                    false),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("Bihar", censusDataCSVS[censusDataCSVS.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnRajasthanAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.TOTAL_AREA,
                                    "./src/test/resources/IndiaStateCensusDataAreaWise.json",
                                    true),
                            IndiaStateCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusFile_WhenStateAreaSortedInAscendingOrder_ShouldReturnRajasthanAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.INDIA, STATE_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusDataCSVS = new Gson().fromJson(censusAnalyser.
                            sorting(Country.INDIA, SortData.SortAccordingTo.TOTAL_AREA,
                                    "./src/test/resources/IndiaStateCensusDataAreaWise.json",
                                    false),
                    IndiaStateCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusDataCSVS[censusDataCSVS.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusFileName_WhenProper_ShouldReturnTrueCountOfEntries() {
        try {
            int numOfRecords = new CensusAnalyser().loadCensusData(Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFileLocation_WhenImProper_ShouldTrowAnExceptionMessage() {
        try {
            new CensusAnalyser().loadCensusData(Country.US, "./src/main/resources/USCensusDataWithWrongHeader.csv"
            );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFileDetails_WhenTypeIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.US, "./src/test/resources/USCensusData.pdf"
            );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("FILE DETAILS MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSFileDetails_WhenDelimiterIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.US, "./src/test/resources/USCensusDataWithWrongDelimiter.csv"
            );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFile_WhenHeaderIncorrect_ShouldThrowAnException() {
        try {
            new CensusAnalyser().loadCensusData(Country.US, "./src/test/resources/USCensusDataWithWrongHeader.csv"
            );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("DELIMITER MISMATCH/HEADER MISMATCH", e.getMessage());
        }
    }

    @Test
    public void givenUSCensusFile_WhenPopulationSortedInDescendingOrder_ShouldReturnCaliforniaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.US, US_CENSUS_CSV_FILE_PATH);
            USCensusDataCSV[] usCensusData = new Gson().fromJson(censusAnalyser.
                            sorting(Country.US, SortData.SortAccordingTo.POPULATION,
                                    "./src/test/resources/USCensusDataPopulationWise.json",
                                    true ),
                            USCensusDataCSV[].class);
            Assert.assertEquals("California", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenUSCensusFile_WhenPopulationSortedInAscendingOrder_ShouldReturnCaliforniaAsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.US, US_CENSUS_CSV_FILE_PATH);
            USCensusDataCSV[] usCensusData = new Gson().fromJson(censusAnalyser.
                            sorting(Country.US, SortData.SortAccordingTo.POPULATION,
                                    "./src/test/resources/USCensusDataPopulationWise.json",
                                    false),
                    USCensusDataCSV[].class);
            Assert.assertEquals("California", usCensusData[usCensusData.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void
    givenUSCensusFile_WhenPopulationDensitySortedInDescendingOrder_ShouldReturnDistrictOfColumbiaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.US, US_CENSUS_CSV_FILE_PATH);
            USCensusDataCSV[] usCensusData = new Gson().fromJson(censusAnalyser.
                            sorting(Country.US, SortData.SortAccordingTo.POPULATION_DENSITY,
                                    "./src/test/resources/USCensusDataPopulationDensityWise.json",
                                    true ),
                            USCensusDataCSV[].class);
            Assert.assertEquals("District of Columbia", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenStateAreaSortedInDescendingOrder_ShouldReturnAlaskaAsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(Country.US, US_CENSUS_CSV_FILE_PATH);
            USCensusDataCSV[] usCensusData = new Gson().fromJson(censusAnalyser.
                            sorting(Country.US, SortData.SortAccordingTo.TOTAL_AREA,
                                    "./src/test/resources/USCensusDataTotalAreaWise.json",
                                    true),
                    USCensusDataCSV[].class);
            Assert.assertEquals("Alaska", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusFile_WhenNotLoadedBeforeSorting_ShouldThrowAnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            USCensusDataCSV[] census = new Gson().fromJson(censusAnalyser.
                            sorting(Country.US, SortData.SortAccordingTo.POPULATION_DENSITY ,
                                    "./src/test/resources/USCensusDataTotalAreaWise.json",
                                    false ),
                            USCensusDataCSV[].class);
        } catch (Exception e) {
            Assert.assertEquals("NO ELEMENTS IN LIST TO SORT",e.getMessage());
        }
    }

    @Test
    public void givenUSAndIndiaCensusFile_ShouldReturnDataOfMostAsMostDenselyPopulousState()
            throws CensusAnalyserException {
        Assert.assertEquals("District of Columbia",
                            new CensusAnalyser().getMostDenseState(US_CENSUS_CSV_FILE_PATH,
                                                                    STATE_CENSUS_CSV_FILE_PATH));
    }
}