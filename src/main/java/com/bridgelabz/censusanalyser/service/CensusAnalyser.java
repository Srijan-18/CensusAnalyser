package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.util.CensusLoader;
import com.bridgelabz.censusanalyser.util.CensusUtilities;
import com.bridgelabz.censusanalyser.util.Country;
import com.bridgelabz.censusanalyser.util.SortData;
import com.bridgelabz.censusanalyser.util.SortData.SortAccordingTo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;

    /**
     * TASK: Constructor to initialise Map.
     */
    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    /**
     * TASK: To load Census Data in Map and return size of map
     * @param csvClass
     * @param csvFilePath
     * @return size of Map
     * @throws CensusAnalyserException
     */
    public int loadCensusData(Class csvClass, String... csvFilePath) throws CensusAnalyserException {
     censusMap = new CensusLoader().loadCSVInMap(csvClass, csvFilePath);
     return censusMap.size();
 }

    /**
     * TASK: to take field name and order of sorting and save file and return sorted list in json format.
     * @param country
     * @param field
     * @param jsonLocation
     * @return string of data in json format
     */
    public String sorting(Country country, SortAccordingTo field, String jsonLocation) throws CensusAnalyserException {
        this.checkEmpty(censusMap);
        List sortedList = censusMap.values().stream()
                .sorted(new SortData().getComparator(field))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        new CensusUtilities().writeIntoJson(jsonLocation, sortedList);
        return new Gson().toJson(sortedList);
    }

    /**
     * TASK: To return Data of most densely populated state among India and US.
     * @param censusCsvFilePathOfUS
     * @param censusCsvFilePathOfIndia
     * @return censusDAO
     * @throws CensusAnalyserException
     */
    public String getMostDenseState(String censusCsvFilePathOfUS, String censusCsvFilePathOfIndia)
            throws CensusAnalyserException {
        int a = this.loadCensusData(IndiaStateCensusCSV.class, censusCsvFilePathOfIndia);
        IndiaStateCensusCSV[] censusIndia = new Gson().fromJson(this.sorting(Country.INDIA,
                SortAccordingTo.POPULATION_DENSITY,
                "./src/test/resources/IndiaStateCensusDataPopulationDensityWise.json"),
                IndiaStateCensusCSV[].class);
        censusMap=new HashMap<>();
        this.loadCensusData(USCensusDataCSV.class, censusCsvFilePathOfUS);
        USCensusDataCSV[] censusUS=new Gson().fromJson(this.sorting(Country.US, SortAccordingTo.POPULATION_DENSITY,
                "./src/test/resources/USCensusDataPopulationDensityWise.json"), USCensusDataCSV[].class);
        if((double)censusIndia[0].densityPerSqKm > censusUS[0].populationDensity)
            return censusIndia[0].state;
        return censusUS[0].state;
    }

    /**
     * TASK: To check if Map is empty , if empty throw custom exception.
     *
     * @param mapToCheck
     * @throws CensusAnalyserException
     */
    private void checkEmpty(Map mapToCheck) throws CensusAnalyserException {
        if (mapToCheck.size() == 0 || mapToCheck == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
    }
}