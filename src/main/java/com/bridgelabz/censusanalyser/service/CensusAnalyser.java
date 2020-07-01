package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.bridgelabz.censusanalyser.model.StateCodeCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    /**
     * TASK: to generate count of entries in StateCensusCSV
     * @param csvFilePath
     * @return count of entries in StateCensusCSV obtained from getCount
     * @throws CensusAnalyserException
     */
    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensusCSV> stateCensusCSVIterator = csvBuilder.
                                                                getCSVFileIterator(reader,StateCensusCSV.class);
            Iterable<StateCensusCSV> stateCensusCSVIterable = () -> stateCensusCSVIterator;
            return this.getCount(stateCensusCSVIterator);
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                                                "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                                                "ERROR IN READING FILE");
        }catch (CSVBuilderException e) {
            throw  new CensusAnalyserException(e.type.name(),e.getMessage());
        }
    }

    /**
     * TASK: to generate count of entries in StateCodeDataCSV
     * @param csvFilePath
     * @return count of entries in StateCodeDataCSV obtained from getCount
     * @throws CensusAnalyserException
     */
    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> stateCodeCSVIterator = csvBuilder.
                                                                getCSVFileIterator(reader,StateCodeCSV.class);
            Iterable<StateCodeCSV> stateCodeCSVIterable = () -> stateCodeCSVIterator;
            return this.getCount(stateCodeCSVIterator);
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                                                "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                                                "ERROR IN READING FILE");
        } catch (CSVBuilderException e) {
            throw  new CensusAnalyserException(e.type.name(),e.getMessage());
        }
    }

    /**
     * TASK: to generate count of entries based on type of iterator passed in parameters
     * @param iterator
     * @param <E>
     * @return count of entries of CSV file whose iterator is passed
     */
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
}