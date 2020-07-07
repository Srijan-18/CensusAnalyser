package com.bridgelabz.censusanalyser.util;

import com.bridgelabz.censusanalyser.dao.CensusDAO;

import java.util.Comparator;

public class SortData {
    public enum SortAccordingTo {
        STATE_NAME,POPULATION,POPULATION_DENSITY,TOTAL_AREA, STATE_CODE;
    }

    public Comparator<CensusDAO> getComparator(SortAccordingTo field) {
        switch (field) {
            case STATE_NAME:
                return Comparator.comparing(CensusDAO::getStateName);
            case POPULATION_DENSITY:
                return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
            case POPULATION:
                return Comparator.comparing(CensusDAO::getPopulation).reversed();
            case TOTAL_AREA:
                return  Comparator.comparing(CensusDAO::getTotalArea).reversed();
            case STATE_CODE:
                return  Comparator.comparing(CensusDAO::getStateCode);
            default:
                return null;
        }
    }
}
