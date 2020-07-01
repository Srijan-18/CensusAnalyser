package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    /**
     * TASK: to get iterator of POJO class type passed in parameters corresponding to the Reader of the given csv file.
     * @param reader
     * @param csvClass
     * @return iterato
     * @throws CensusAnalyserException
     */
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
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
