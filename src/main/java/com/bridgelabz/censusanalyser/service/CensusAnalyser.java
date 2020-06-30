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
        try {
            Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<StateCensusCSV> csvToBeanBuilder=new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true).withSeparator(',');
            CsvToBean<StateCensusCSV> csvToBean=csvToBeanBuilder.build();
            Iterator<StateCensusCSV> stateCensusCSVIterator=csvToBean.iterator();
            Iterable<StateCensusCSV> stateCensusCSVIterable=() -> stateCensusCSVIterator;
            int numOfEntries= (int) StreamSupport.stream(stateCensusCSVIterable.spliterator(),false).count();
            return numOfEntries;
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.DELIMITER_MISMATCH,
                    "DELIMITER MISMATCH");
        }
        catch (NoSuchFileException e) {
                throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                        "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        }
    }
    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<StateCodeCSV> csvToBeanBuilder=new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCodeCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true).withSeparator(',');
            CsvToBean<StateCodeCSV> csvToBean=csvToBeanBuilder.build();
            Iterator<StateCodeCSV> stateCodeCSVIterator=csvToBean.iterator();
            Iterable<StateCodeCSV> stateCodeCSVIterable=() -> stateCodeCSVIterator;
            int numOfEntries= (int) StreamSupport.stream(stateCodeCSVIterable.spliterator(),false).count();
            return numOfEntries;
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.DELIMITER_MISMATCH,
                    "DELIMITER MISMATCH");
        }
        catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        }
    }
}