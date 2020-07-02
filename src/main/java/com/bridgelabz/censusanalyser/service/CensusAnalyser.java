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
            List<StateCodeCSV> stateCodeCSVList = this.getList(stateCodeCSVIterator);
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
        this.sort(censusCSVComparator);
        String sortedStateCensusJson=new Gson().toJson(stateCensusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<StateCensusCSV> censusCSVComparator) {
        for (int i = 0; i < stateCensusCSVList.size() - 1; i++) {
            for (int j = 0; j < stateCensusCSVList.size() - i - 1; j++) {
                StateCensusCSV census1 = stateCensusCSVList.get(j);
                StateCensusCSV census2 = stateCensusCSVList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    stateCensusCSVList.set(j, census2);
                    stateCensusCSVList.set(j + 1, census1);
                }
            }
        }
    }
}