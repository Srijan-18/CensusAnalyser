package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
   public enum ExceptionType{
       IMPROPER_FILE_DETAILS;
   }
    ExceptionType type;

    public CensusAnalyserException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
