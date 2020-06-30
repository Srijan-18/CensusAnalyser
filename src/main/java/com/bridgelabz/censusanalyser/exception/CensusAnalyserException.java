package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
   public enum ExceptionType{
       IMPROPER_FILE_DETAILS,INPUT_OUTPUT_EXCEPTION,DELIMITER_MISMATCH,WRONG_DELIMITER;
   }
    ExceptionType type;

    public CensusAnalyserException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
