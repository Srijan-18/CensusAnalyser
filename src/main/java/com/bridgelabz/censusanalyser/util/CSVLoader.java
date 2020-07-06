package com.bridgelabz.censusanalyser.util;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.CensusDAO;
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

public class CSVLoader {
    Map<String, CensusDAO> censusMap = new HashMap();

    /**
     * TASK: to load given census file in a map and return the loaded map
     * @param filePath
     * @param censusCSVClass
     * @return map loaded with census
     * @throws CensusAnalyserException
     */
    public <T> Map loadCSVInMap(String filePath, Class<T> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T>  censusIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusIterable = () -> censusIterator;
            switch (censusCSVClass.getSimpleName())
            {
                case "IndiaStateCensusCSV" :
                    StreamSupport.stream(censusIterable.spliterator(), false)
                            .map(IndiaStateCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
                case "USCensusDataCSV" :
                    StreamSupport.stream(censusIterable.spliterator(), false)
                            .map(USCensusDataCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
                case "IndiaStateCodeCSV" :
                    StreamSupport.stream(censusIterable.spliterator(), false)
                            .map(IndiaStateCodeCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.stateName, new CensusDAO(csvState)));
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