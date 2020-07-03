package com.bridgelabz.censusanalyser.model;

public class IndiaCensusDAO {
    public String stateName;
    public Integer tin;
    public String stateCode;
    public Integer srNo;

    public IndiaCensusDAO(IndiaStateCodeCSV stateCodeCSVIterator) {
       srNo = stateCodeCSVIterator.srNo;
       stateCode = stateCodeCSVIterator.stateCode;
       tin = stateCodeCSVIterator.tin;
       stateName = stateCodeCSVIterator.stateName;
    }
}