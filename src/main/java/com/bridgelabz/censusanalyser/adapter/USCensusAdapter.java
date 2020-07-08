package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter{
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMapUS = super.loadCSVInMap(USCensusDataCSV.class, csvFilePath);
        return censusMapUS;
    }
}
