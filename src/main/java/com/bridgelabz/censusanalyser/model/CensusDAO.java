package com.bridgelabz.censusanalyser.model;

public class CensusDAO {
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String stateName;
    public Integer tin;
    public String stateCode;
    public Integer srNo;

    public String stateId;
    public Integer housingUnits;
    public Double totalArea;
    public Double waterArea;
    public Double landArea;
    public Double populationDensity;
    public Double housingDensity;


    public CensusDAO(IndiaStateCodeCSV stateCodeCSV) {
       srNo = stateCodeCSV.srNo;
       stateCode = stateCodeCSV.stateCode;
       tin = stateCodeCSV.tin;
       stateName = stateCodeCSV.stateName;
    }

    public CensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        areaInSqKm = indiaStateCensusCSV.areaInSqKm;
        densityPerSqKm = indiaStateCensusCSV.densityPerSqKm;
        population = indiaStateCensusCSV.population;
        stateName=indiaStateCensusCSV.state;
    }

    public CensusDAO(USCensusDataCSV usCensusDataCSV)
    {
        housingDensity = usCensusDataCSV.housingDensity;
        housingUnits = usCensusDataCSV.housingUnits;
        landArea = usCensusDataCSV.landArea;
        population = usCensusDataCSV.population;
        stateName = usCensusDataCSV.state;
        totalArea = usCensusDataCSV.totalArea;
        waterArea = usCensusDataCSV.waterArea;
        populationDensity = usCensusDataCSV.populationDensity;
        stateId = usCensusDataCSV.stateId;
    }
}