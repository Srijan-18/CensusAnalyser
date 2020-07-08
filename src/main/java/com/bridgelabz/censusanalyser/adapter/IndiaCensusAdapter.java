package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter {
    @Override
    public Map loadCensusData(String... filePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMapIndia = super.loadCSVInMap(IndiaStateCensusCSV.class, filePath);
        return censusMapIndia;
    }
}
