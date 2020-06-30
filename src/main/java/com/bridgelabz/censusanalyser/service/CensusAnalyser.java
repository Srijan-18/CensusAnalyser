package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.bridgelabz.censusanalyser.model.StateCodeCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<StateCensusCSV> stateCensusCSVIterator = this.getCSVFileIterator(reader,StateCensusCSV.class);
            Iterable<StateCensusCSV> stateCensusCSVIterable = () -> stateCensusCSVIterator;
            int numOfEntries = (int) StreamSupport.stream(stateCensusCSVIterable.spliterator(), false).count();
            return numOfEntries;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<StateCodeCSV> stateCodeCSVIterator = this.getCSVFileIterator(reader,StateCodeCSV.class);
            Iterable<StateCodeCSV> stateCodeCSVIterable = () -> stateCodeCSVIterator;
            int numOfEntries = (int) StreamSupport.stream(stateCodeCSVIterable.spliterator(), false).count();
            return numOfEntries;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        }
    }
    private <E> Iterator getCSVFileIterator(Reader reader,Class csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true).withSeparator(',');
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.DELIMITER_MISMATCH,
                    "DELIMITER MISMATCH/HEADER MISMATCH");
        }
    }
}