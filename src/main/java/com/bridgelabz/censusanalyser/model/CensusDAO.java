package com.bridgelabz.censusanalyser.model;

public class CensusDAO {
    public String stateName;
    public String stateCode;

    public String stateId;
    public Double totalArea;
    public Double populationDensity;
    public Integer population;

    public CensusDAO(IndiaStateCodeCSV stateCodeCSV) {
        stateCode = stateCodeCSV.stateCode;
        stateName = stateCodeCSV.stateName;
    }

    public CensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        totalArea = (double) indiaStateCensusCSV.areaInSqKm;
        populationDensity = (double) indiaStateCensusCSV.densityPerSqKm;
        population = indiaStateCensusCSV.population;
        stateName = indiaStateCensusCSV.state;
    }

    public CensusDAO(USCensusDataCSV usCensusDataCSV)
    {
        population = usCensusDataCSV.population;
        stateName = usCensusDataCSV.state;
        totalArea = usCensusDataCSV.totalArea;
        populationDensity = usCensusDataCSV.populationDensity;
        stateId = usCensusDataCSV.stateId;
    }
}