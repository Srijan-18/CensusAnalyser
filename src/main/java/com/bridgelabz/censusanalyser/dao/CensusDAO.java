package com.bridgelabz.censusanalyser.dao;

import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.util.Country;

public class CensusDAO {
    public String stateName;
    public String stateCode;

    public String stateId;
    public Double totalArea;
    public Double populationDensity;
    public Integer population;

    public String getStateCode() {
        return stateCode;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public Double getPopulationDensity() {
        return populationDensity;
    }

    public Integer getPopulation() {
        return population;
    }

    public String getStateName() {
        return stateName;
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

    public Object getCensusDTO(Country country) {
        switch (country) {
            case INDIA:
                return new IndiaStateCensusCSV(stateName, population, totalArea, populationDensity);
            case US:
                return new USCensusDataCSV(stateName, population, totalArea, populationDensity);
            case INDIA_STATE_CODE:
                return new IndiaStateCodeCSV(stateName,stateCode);
            default:
                return null;
        }
    }
}