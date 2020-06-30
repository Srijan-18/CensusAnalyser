package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {
    @CsvBindByName
    public Integer SrNo;

    @CsvBindByName(column = "StateName", required = true)
    public String StateName;

    @CsvBindByName
    public Integer TIN;

    @CsvBindByName
    public String StateCode;

    @Override
    public String toString()
    {
        return "IndiaStateCodeCSV{" +
                "SrNo=" + SrNo +
                ", StateName='" + StateName + '\'' +
                ", TIN=" + TIN +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}

