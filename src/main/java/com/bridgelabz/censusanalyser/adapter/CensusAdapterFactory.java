package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.util.Country;

import java.util.Map;

public class CensusAdapterFactory {

    public static Map<String, CensusDAO> getCensusAdapter(Country country, String... csvFilePath)
                                                                    throws CensusAnalyserException {
        if (country.equals(Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        return new USCensusAdapter().loadCensusData(csvFilePath);
        }
    }