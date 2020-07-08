package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    /**
     * TASK: to load US Census Data in a Map by invoking loadCSVInMap method of CensusAdapter(parent) class.
     * @param filePath
     * @return Loaded Map
     * @throws CensusAnalyserException
     */
    @Override
    public Map<String, CensusDAO> loadCensusData(String... filePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMapUS = super.loadCSVInMap(USCensusDataCSV.class, filePath);
        return censusMapUS;
    }
}
