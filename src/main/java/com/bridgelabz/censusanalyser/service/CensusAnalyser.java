package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.util.CensusUtilities;
import com.google.gson.Gson;
import csvbuilder.exception.CSVBuilderException;
import csvbuilder.service.CSVBuilderFactory;
import csvbuilder.service.ICSVBuilder;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
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
            Iterable<IndiaStateCensusCSV> censusDAOIterable = () -> censusDAOIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false).
                    forEach(censusData -> censusMap.put(censusData.state, new CensusDAO(censusData)));
            return censusMap.size();
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
            StreamSupport.stream(stateCodeIterable.spliterator(), false).
                    forEach(censusData -> censusMap.put(censusData.stateName, new CensusDAO(censusData)));
            return censusMap.size();
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
     * @param mapToConvert
     * @return list
     */
    private List getList(Map<String, CensusDAO> mapToConvert) {
        List<CensusDAO> censusList = mapToConvert.values().stream().collect(Collectors.toList());
        return censusList;
    }

    /**
     * TASK: To Load USCensus Data File from given Path
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusDataCSV> stateCodeIterator = csvBuilder.
                    getCSVFileIterator(reader, USCensusDataCSV.class);
            Iterable<USCensusDataCSV> stateCodeIterable = () -> stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false).
                    forEach(censusData -> censusMap.put(censusData.state, new CensusDAO(censusData)));
            return censusMap.size();
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
     * TASK: to take field name and order of sorting and save file and return sorted list in json format.
     * @param fieldName
     * @param inReverseOrder
     * @return
     * @throws CensusAnalyserException
     */
    public String sortDataToJSON(String fileAddress,String fieldName, boolean inReverseOrder)
            throws CensusAnalyserException, NoSuchFieldException {
        this.checkEmpty(censusMap);
        CensusUtilities censusUtilities = new CensusUtilities<CensusDAO>();
        List sortedList = censusUtilities.sortList(this.getList(censusMap),fieldName,inReverseOrder);
        censusUtilities.writeIntoJson(fileAddress, sortedList);
        return new Gson().toJson(sortedList);
    }

    /**
     * TASK: To return Data of most densely populated state among India and US.
     * @param censusCsvFilePathOfUS
     * @param censusCsvFilePathOfIndia
     * @return censusDAO
     * @throws CensusAnalyserException
     * @throws NoSuchFieldException
     */
    public CensusDAO getMostDenseState(String censusCsvFilePathOfUS, String censusCsvFilePathOfIndia) throws CensusAnalyserException, NoSuchFieldException {
        this.loadStateCensusData(censusCsvFilePathOfIndia);
        CensusDAO[] censusDAOIndia=new Gson().fromJson(this.sortDataToJSON("Test.json","densityPerSqKm",true),CensusDAO[].class);
        censusMap=new HashMap<>();
        this.loadUSCensusData(censusCsvFilePathOfUS);
        CensusDAO[] censusDAOUS=new Gson().fromJson(this.sortDataToJSON("Test.json","populationDensity",true),CensusDAO[].class);
        if((double)censusDAOIndia[0].densityPerSqKm > censusDAOUS[0].populationDensity)
            return censusDAOIndia[0];
        return censusDAOUS[0];
    }

    /**
     * TASK: To check if list is empty , if empty throw custom exception.
     *
     * @param mapToCheck
     * @throws CensusAnalyserException
     */
    private void checkEmpty(Map mapToCheck) throws CensusAnalyserException {
        if (mapToCheck.size() == 0 || mapToCheck == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
    }
}