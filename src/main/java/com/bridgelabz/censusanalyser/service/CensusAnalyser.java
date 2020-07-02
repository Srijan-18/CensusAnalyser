package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.bridgelabz.censusanalyser.model.StateCodeCSV;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import csvbuilder.exception.CSVBuilderException;
import csvbuilder.service.CSVBuilderFactory;
import csvbuilder.service.ICSVBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<StateCensusCSV> stateCensusCSVList = null;
    List<StateCodeCSV> stateCodeCSVList=null;

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
            Iterator<StateCensusCSV> stateCensusCSVIterator = csvBuilder.
                    getCSVFileIterator(reader, StateCensusCSV.class);
            stateCensusCSVList = this.getList(stateCensusCSVIterator);
            return stateCensusCSVList.size();
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
            Iterator<StateCodeCSV> stateCodeCSVIterator = csvBuilder.
                    getCSVFileIterator(reader, StateCodeCSV.class);
            stateCodeCSVList = this.getList(stateCodeCSVIterator);
            return stateCodeCSVList.size();
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
        if(stateCensusCSVList.size() == 0 || stateCensusCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                                            "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.state);
        this.sortAscending(censusCSVComparator,stateCensusCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCensusCSVList);
        return sortedStateCensusJson;
    }

    /**
     * TASK: To sort State Code Data in alphabetical order according to state code
     * @return sorted state code data in json format
     * @throws CensusAnalyserException
     */
    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if(stateCodeCSVList.size() == 0 || stateCodeCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCodeCSV> censusCSVComparator=Comparator.comparing(census->census.StateCode);
        this.sortAscending(censusCSVComparator,stateCodeCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCodeCSVList);
        return sortedStateCensusJson;
    }

    /**
     * TASK: To sort according to population in ascending order and return state census data in json format
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationSortedCensusData() throws CensusAnalyserException {
        if(stateCensusCSVList.size() == 0 || stateCensusCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.population);
        this.sortDescending(censusCSVComparator,stateCensusCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCensusCSVList);
        this.writeIntoJson("./src/test/resources/StateCensusJSON.json", stateCensusCSVList);
        return sortedStateCensusJson;
    }

    /**
     * TASK: Generic Method to sort entries in ascending order
     * @param censusCSVComparator
     * @param listToSort
     * @param <T>
     */
    private <T> void sortAscending(Comparator<T> censusCSVComparator, List listToSort) {
        for (int i = 0; i < listToSort.size() - 1; i++) {
            for (int j = 0; j < listToSort.size() - i - 1; j++) {
                T census1 = (T) listToSort.get(j);
                T census2 = (T) listToSort.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    listToSort.set(j, census2);
                    listToSort.set(j + 1, census1);
                }
            }
        }
    }

    /**
     * TASK: Generic Method to sort entries in descending order
     * @param censusCSVComparator
     * @param listToSort
     * @param <T>
     */
    private <T> void sortDescending(Comparator<T> censusCSVComparator,List listToSort) {
        for (int i = 0; i < listToSort.size() - 1; i++) {
            for (int j = 0; j < listToSort.size() - i - 1; j++) {
                T census1 = (T) listToSort.get(j);
                T census2 = (T) listToSort.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) < 0) {
                    listToSort.set(j, census2);
                    listToSort.set(j + 1, census1);
                }
            }
        }
    }

    /**
     * TASK: to write a list into a json file
     * @param fileName
     * @param listToWrite
     */
    private void writeIntoJson(String fileName,List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}