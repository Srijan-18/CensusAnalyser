package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.util.Country;

import java.util.Map;

public class CensusAdapterFactory {
    /**
     * TASK: To return loaded map by invoking adapter class's(corresponding to country) loadCensusData method.
     * @param country
     * @param csvFilePath
     * @return map
     * @throws CensusAnalyserException
     */
    public static Map<String, CensusDAO> getCensusAdapter(Country country, String... csvFilePath)
                                                                    throws CensusAnalyserException {
        if (country.equals(Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        return new USCensusAdapter().loadCensusData(csvFilePath);
        }
    }