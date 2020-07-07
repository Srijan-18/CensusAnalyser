package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public Integer srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public Integer tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    public IndiaStateCodeCSV() {
    }

    public IndiaStateCodeCSV(String stateName, String stateCode) {
        this.stateName = stateName;
        this.stateCode = stateCode;
    }
}