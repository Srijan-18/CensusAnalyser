package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
    public CensusAnalyserException(String name, String message) {
       super(message);
       this.type=ExceptionType.valueOf(name);
    }

    public enum ExceptionType{
       IMPROPER_FILE_DETAILS, INPUT_OUTPUT_EXCEPTION, DELIMITER_MISMATCH, NO_ELEMENTS, NO_SUCH_FIELD, INVALID_CLASS_DETAILS;
   }
    ExceptionType type;

    public CensusAnalyserException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
