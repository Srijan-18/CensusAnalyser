package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
   public enum ExceptionType{
       ENTRIES_MISMATCH;
   }
    ExceptionType type;

    public CensusAnalyserException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
