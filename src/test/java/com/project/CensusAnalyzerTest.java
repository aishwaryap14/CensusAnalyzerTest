package com.project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyzerTest
{
    public static String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    public static String WRONG_FILE_PATH = "src/main/resources/IndiaStateCensusData.csv";

    @Test
    public void givenIndiaCensusCSVFile_shouldReturnCorrectRecords()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            int numOfRecords = censusAnalyzerMain.loadIndiaCensusFile(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        }
        catch (CensusAnalyzerException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void givenIndiaCensusCSVFileofWrongPath_shouldReturnCorrectRecords()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyzerException.class);
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_FILE_PATH);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }

    }

}
