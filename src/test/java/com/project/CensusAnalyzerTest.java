package com.project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyzerTest
{
    public static String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    public static String WRONG_CENSUSFILE_PATH = "src/main/resources/IndiaStateCensusData.csv";
    public static String INDIA_STATE_CODE_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    public static String WRONG_STATEFILE_PATH = "src/main/resources/IndiaStateCode.csv";

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
    public void givenIndiaCensusCSVFileofWrongPath_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyzerException.class);
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_CENSUSFILE_PATH);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }

    }

    @Test
    public void givenIndiaStateCodeCsvFile_ShouldReturnCorrectRecords()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            int numOfRecords = censusAnalyzerMain.loadIndiaStateCodeFile(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        }
        catch (CensusAnalyzerException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCSVFileofWrongPath_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyzerException.class);
            censusAnalyzerMain.loadIndiaStateCodeFile(WRONG_STATEFILE_PATH);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

}
