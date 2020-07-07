package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public Integer population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public Integer areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Integer densityPerSqKm;

    public IndiaStateCensusCSV(String stateName, Integer population, Double totalArea, Double populationDensity) {
        this.state = stateName;
        this.population = population;
        this.densityPerSqKm = (int) Math.floor(populationDensity);
        this.areaInSqKm = (int) Math.floor(totalArea);
    }
    public IndiaStateCensusCSV(){

    }
}
