package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.bridgelabz.censusanalyser.model.StateCodeCSV;
import com.google.gson.Gson;
import csvbuilder.exception.CSVBuilderException;
import csvbuilder.service.CSVBuilderFactory;
import csvbuilder.service.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
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
     * TASK: to generate count of entries based on type of iterator passed in parameters
     *
     * @param iterator
     * @param <E>
     * @return count of entries of CSV file whose iterator is passed
     */
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
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

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if(stateCensusCSVList.size() == 0 || stateCensusCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                                            "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.state);
        this.sort(censusCSVComparator,stateCensusCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCensusCSVList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if(stateCodeCSVList.size() == 0 || stateCodeCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCodeCSV> censusCSVComparator=Comparator.comparing(census->census.StateCode);
        this.sort(censusCSVComparator,stateCodeCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCodeCSVList);
        return sortedStateCensusJson;
    }

    private <T> void sort(Comparator<T> censusCSVComparator,List listToSort) {
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

    public String getPopulationSortedCensusData() throws CensusAnalyserException {
        if(stateCensusCSVList.size() == 0 || stateCensusCSVList == null)
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_ELEMENTS,
                    "NO ELEMENTS IN LIST TO SORT");
        Comparator<StateCensusCSV> censusCSVComparator=Comparator.comparing(census->census.population);
        this.sortDescending(censusCSVComparator,stateCensusCSVList);
        String sortedStateCensusJson=new Gson().toJson(stateCensusCSVList);
        return sortedStateCensusJson;
    }
}