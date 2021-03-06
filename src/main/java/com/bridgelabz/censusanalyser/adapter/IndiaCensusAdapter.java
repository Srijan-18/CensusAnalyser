package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter {
    /**
     * TASK: to load India Census Data in a Map by invoking loadCSVInMap method of CensusAdapter(parent) class.
     * @param filePath
     * @return Loaded Map
     * @throws CensusAnalyserException
     */
    @Override
    public Map loadCensusData(String... filePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMapIndia = super.loadCSVInMap(IndiaStateCensusCSV.class, filePath);
        return censusMapIndia;
    }
}
