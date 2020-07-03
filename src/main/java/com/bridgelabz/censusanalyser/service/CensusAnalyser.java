package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.util.CensusUtilities;
import com.google.gson.Gson;
import csvbuilder.exception.CSVBuilderException;
import csvbuilder.service.CSVBuilderFactory;
import csvbuilder.service.ICSVBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String,IndiaCensusDAO> indiaCensusMap;

    public CensusAnalyser() {
        this.indiaCensusMap =new HashMap<>();
    }

    /**
     * TASK: to generate count of entries in StateCensusCSV
     *
     * @param csvFilePath
     * @return count of entries in StateCensusCSV obtained from getCount
     * @throws CensusAnalyserException
     */
    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCensusCSV> censusDAOIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            Iterable<IndiaStateCensusCSV>  censusDAOIterable=()-> censusDAOIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(),false).
                    forEach(censusData -> indiaCensusMap.put(censusData.state ,new IndiaCensusDAO(censusData)));
            return indiaCensusMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.type.name(), e.getMessage());
        }
    }

    /**
     * TASK: to generate count of entries in StateCodeDataCSV
     *
     * @param csvFilePath
     * @return count of entries in StateCodeDataCSV obtained from getCount
     * @throws CensusAnalyserException
     */
    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> stateCodeIterable = () -> stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(),false).
                    forEach(censusData -> indiaCensusMap.put(censusData.stateName ,new IndiaCensusDAO(censusData)));
            return indiaCensusMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.type.name(), e.getMessage());
        }
    }

    /**
     * TASK: to convert map to a list
     *
     * @param Map
     * @param <E>
     * @return list
     */
    private <E> List getList(Map<String,IndiaCensusDAO> mapToConvert) {
        List<IndiaCensusDAO> censusList = mapToConvert.values().stream()
                .collect(Collectors.toList());
        return censusList;
    }

    /**
     * TASK: To sort state census data in alphabetical order according to state name
     * @return sorted state census in json format
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(this.indiaCensusMap);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.stateName);
        return new Gson().toJson(new CensusUtilities<IndiaCensusDAO>()
                .sortAscending(censusCSVComparator, this.getList(this.indiaCensusMap)));
    }

    /**
     * TASK: To sort State Code Data in alphabetical order according to state code
     * @return sorted state code data in json format
     * @throws CensusAnalyserException
     */
    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        this.checkEmpty(this.indiaCensusMap);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.stateCode);
        return new Gson().toJson(new CensusUtilities<IndiaCensusDAO>()
                        .sortAscending(censusCSVComparator, this.getList(this.indiaCensusMap)));
    }

    /**
     * TASK: To sort according to population in ascending order and return state census data in json format
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(this.indiaCensusMap);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.population);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaCensusDAO>();
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json",
                                censusUtilities.sortDescending(censusCSVComparator,this.getList(this.indiaCensusMap)));
        return new Gson().toJson(censusUtilities.sortDescending(censusCSVComparator,this.getList(this.indiaCensusMap)));
    }

    /**
     * TASK: To sort according to population density in descending order and return state census data in json format
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationDensitySortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(this.indiaCensusMap);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.densityPerSqKm);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaCensusDAO>();
        censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap));
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json",
                                censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap)));
        return new Gson().toJson(censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap)));
    }

    /**
     * TASK: To sort the list according to state area and return the result String in json format
     * @return String in json format
     * @throws CensusAnalyserException
     */
    public String getStateAreaSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(this.indiaCensusMap);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.areaInSqKm);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaCensusDAO>();
        censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap));
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json",
                                censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap)));
        return new Gson().toJson(censusUtilities.sortDescending(censusCSVComparator, this.getList(indiaCensusMap)));
    }

    /**
     * TASK: To check if list is empty , if empty throw custom exception.
     * @param mapToCheck
     * @throws CensusAnalyserException
     */
    private void checkEmpty(Map mapToCheck) throws CensusAnalyserException {
        if(mapToCheck.size() == 0 || mapToCheck == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");

    }
}