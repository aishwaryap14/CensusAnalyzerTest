package com.project;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCsv {
    @CsvBindByName(column = "SrNo", required = true)
public int srno;
    @CsvBindByName(column = "StateName", required = true)
public String stateName;
    @CsvBindByName(column = "TIN", required = true)
public int tin;
    @CsvBindByName(column = "StateCode", required = true)
public String stateCode;

    public IndiaStateCodeCsv()
    {

    }

    public IndiaStateCodeCsv(int srno, String stateName, int tin, String stateCode) {
        this.srno = srno;
        this.stateName = stateName;
        this.tin = tin;
        this.stateCode = stateCode;
    }


}
