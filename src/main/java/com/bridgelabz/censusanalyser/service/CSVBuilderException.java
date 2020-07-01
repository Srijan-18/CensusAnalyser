package com.bridgelabz.censusanalyser.service;

public class CSVBuilderException extends Exception {
    ExceptionType type;

    public CSVBuilderException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        IMPROPER_FILE_DETAILS, INPUT_OUTPUT_EXCEPTION, DELIMITER_MISMATCH;
    }
}