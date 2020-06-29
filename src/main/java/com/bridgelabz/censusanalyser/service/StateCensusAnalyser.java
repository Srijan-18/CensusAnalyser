package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.StateCensusCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<StateCensusCSV> csvToBeanBuilder=new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<StateCensusCSV> csvToBean=csvToBeanBuilder.build();
            Iterator<StateCensusCSV> stateCensusCSVIterator=csvToBean.iterator();
            Iterable<StateCensusCSV> stateCensusCSVIterable=() -> stateCensusCSVIterator;
            int numOfEntries= (int) StreamSupport.stream(stateCensusCSVIterable.spliterator(),false).count();
            return numOfEntries;
        } catch (IOException e) {
          throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                                            "FILE DETAILS MISMATCH");
        }
    }
}
