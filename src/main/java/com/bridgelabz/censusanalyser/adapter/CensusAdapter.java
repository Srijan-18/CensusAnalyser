package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import csvbuilder.exception.CSVBuilderException;
import csvbuilder.service.CSVBuilderFactory;
import csvbuilder.service.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    Map<String, CensusDAO> censusMap = new HashMap();

    /**
     * Abstract method which is to be implemented in respective adapter classes to load data.
     * @param filePath
     * @return
     * @throws CensusAnalyserException
     */
    public abstract Map loadCensusData(String... filePath) throws CensusAnalyserException;

    /**
     * TASK: to load given census file in a map and return the loaded map
     * @param censusCSVClass
     * @param filePath
     * @return map loaded with census
     * @throws CensusAnalyserException
     */
    public <T> Map loadCSVInMap(Class<T> censusCSVClass, String... filePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> censusIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusIterable = () -> censusIterator;
            switch (censusCSVClass.getSimpleName())
            {
                case "IndiaStateCensusCSV" :
                    StreamSupport.stream(censusIterable.spliterator(), false)
                            .map(IndiaStateCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    if(filePath.length>1) {
                        try (Reader codeReader = Files.newBufferedReader(Paths.get(filePath[1]))) {
                            Iterator<IndiaStateCodeCSV> stateCodeIterator = CSVBuilderFactory.createCSVBuilder()
                                    .getCSVFileIterator(codeReader, IndiaStateCodeCSV.class);
                            Iterable<IndiaStateCodeCSV> codeCSVIterable = () -> stateCodeIterator;
                            StreamSupport.stream(codeCSVIterable.spliterator(), false)
                                    .filter(csvState -> censusMap.get(csvState.stateName) != null)
                                    .forEach(csvState -> censusMap.get(csvState.stateName)
                                                                  .stateCode = csvState.stateCode);
                        }
                    }
                        break;
                case "USCensusDataCSV" :
                    StreamSupport.stream(censusIterable.spliterator(), false)
                            .map(USCensusDataCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
                default:
                    censusMap=null;
            }
            return censusMap;
        }
        catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.IMPROPER_FILE_DETAILS,
                    "FILE DETAILS MISMATCH");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INPUT_OUTPUT_EXCEPTION,
                    "ERROR IN READING FILE");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.type.name(), e.getMessage());
        }
    }
}