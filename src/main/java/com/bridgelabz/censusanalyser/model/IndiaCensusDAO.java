package com.bridgelabz.censusanalyser.model;

public class IndiaCensusDAO {
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String stateName;
    public Integer tin;
    public String stateCode;
    public Integer srNo;

    public IndiaCensusDAO(IndiaStateCodeCSV stateCodeCSV) {
       srNo = stateCodeCSV.srNo;
       stateCode = stateCodeCSV.stateCode;
       tin = stateCodeCSV.tin;
       stateName = stateCodeCSV.stateName;
    }

    public IndiaCensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        areaInSqKm = indiaStateCensusCSV.areaInSqKm;
        densityPerSqKm = indiaStateCensusCSV.densityPerSqKm;
        population = indiaStateCensusCSV.population;
        stateName=indiaStateCensusCSV.state;
    }
}