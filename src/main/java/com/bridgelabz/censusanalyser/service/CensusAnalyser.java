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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaStateCensusCSV> indiaStateCensusCSVList = null;
    List<IndiaCensusDAO> censusList=null;

    public CensusAnalyser() {
        censusList=new ArrayList<IndiaCensusDAO>();
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
            Iterator<IndiaStateCensusCSV> stateCensusCSVIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            indiaStateCensusCSVList = this.getList(stateCensusCSVIterator);
            return indiaStateCensusCSVList.size();
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
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCodeCSVIterator.hasNext())
                censusList.add(new IndiaCensusDAO(stateCodeCSVIterator.next()));
            return censusList.size();
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
     * TASK: to convert iterator to a list
     *
     * @param iterator
     * @param <E>
     * @return list
     */
    private <E> List getList(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    /**
     * TASK: To sort state census data in alphabetical order according to state name
     * @return sorted state census in json format
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(indiaStateCensusCSVList);
        Comparator<IndiaStateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.state);
        new CensusUtilities<IndiaStateCensusCSV>().sortAscending(censusCSVComparator, indiaStateCensusCSVList);
        String sortedStateCensusJson=new Gson().toJson(indiaStateCensusCSVList);
        return sortedStateCensusJson;
    }

    /**
     * TASK: To sort State Code Data in alphabetical order according to state code
     * @return sorted state code data in json format
     * @throws CensusAnalyserException
     */
    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        this.checkEmpty(this.censusList);
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.stateCode);
        new CensusUtilities<IndiaCensusDAO>().sortAscending(censusCSVComparator, this.censusList);
        return new Gson().toJson(this.censusList);
    }

    /**
     * TASK: To sort according to population in ascending order and return state census data in json format
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(indiaStateCensusCSVList);
        Comparator<IndiaStateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.population);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaStateCensusCSV>();
        censusUtilities.sortDescending(censusCSVComparator, indiaStateCensusCSVList);
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json", indiaStateCensusCSVList);
        return new Gson().toJson(indiaStateCensusCSVList);
    }

    /**
     * TASK: To sort according to population density in descending order and return state census data in json format
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationDensitySortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(indiaStateCensusCSVList);
        Comparator<IndiaStateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.densityPerSqKm);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaStateCensusCSV>();
        censusUtilities.sortDescending(censusCSVComparator, indiaStateCensusCSVList);
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json", indiaStateCensusCSVList);
        return new Gson().toJson(indiaStateCensusCSVList);
    }

    /**
     * TASK: To sort the list according to state area and return the result String in json format
     * @return String in json format
     * @throws CensusAnalyserException
     */
    public String getStateAreaSortedCensusData() throws CensusAnalyserException {
        this.checkEmpty(indiaStateCensusCSVList);
        Comparator<IndiaStateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.areaInSqKm);
        CensusUtilities censusUtilities=new CensusUtilities<IndiaStateCensusCSV>();
        censusUtilities.sortDescending(censusCSVComparator, indiaStateCensusCSVList);
        censusUtilities.writeIntoJson("./src/test/resources/StateCensusJSON.json", indiaStateCensusCSVList);
        return new Gson().toJson(indiaStateCensusCSVList);
    }

    /**
     * TASK: To check if list is empty , if empty throw custom exception.
     * @param listToCheck
     * @throws CensusAnalyserException
     */
    private void checkEmpty(List listToCheck) throws CensusAnalyserException {
        if(listToCheck.size() == 0 || listToCheck == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");

    }
}