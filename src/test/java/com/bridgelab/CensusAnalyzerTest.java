package com.bridgelab;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;


public class CensusAnalyzerTest
{
    public static String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    public static String WRONG_CENSUSFILE_PATH = "src/main/resources/IndiaStateCensusData.csv";
    public static String WRONG_CENSUS_CSV_FILE_TYPE = "src/test/resources/IndiaStateCensusData.txt";
    public static String WRONG_CENSUS_CSV_FILE_HEADER = "src/test/resources/IndiaStateCensusDataHeader.csv";
    public static String WRONG_CENSUS_CSV_FILE_DELIMETER = "src/test/resources/IndiaStateCensusDataDelimeter.csv";

    public static String INDIA_STATE_CODE_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    public static String WRONG_STATEFILE_PATH = "src/main/resources/IndiaStateCode.csv";
    public static String WRONG_STATE_CODE_FILE_TYPE = "src/test/resources/IndiaStateCode.csv";
    public static String WRONG_STATE_CODE_FILE_HEADER = "src/test/resources/IndiaStateCodeHeader.csv";
    public static String WRONG_STATE_CODE_FILE_DELIMETER = "src/test/resources/IndiaStateCodeDelimeter.csv";

    public static String US_CENSUS_CSV_FILE_PATH = "src/test/resources/USCensusData.csv";
    private static final double DELTA = 1e-15;

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
        { }
    }


    @Test
    public void givenIndiaCensusCSVFileofWrongPath_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_CENSUSFILE_PATH);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCSVFileofWrongHeader_shouldReturnException() {
        try {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_CENSUS_CSV_FILE_HEADER);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVFileofWrongDelimeter_shouldReturnException() {
        try {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_CENSUS_CSV_FILE_DELIMETER);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVFileofWrongType_shouldReturnException() {
        try {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaCensusFile(WRONG_CENSUS_CSV_FILE_TYPE);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }

    @Test
    public void whenGivenCensusCsvData_whenSortedOnState_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            String sortedData = censusAnalyzerMain.sortIndiaJsonData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }
        catch (CensusAnalyzerException e){}
    }

    //-----------------------------------------------------------------------------------//
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
        { }
    }

    @Test
    public void givenIndiaStateCSVFileofWrongPath_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaStateCodeFile(WRONG_STATEFILE_PATH);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCSVFileofWrongHeader_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaStateCodeFile(WRONG_STATE_CODE_FILE_HEADER);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaStateCSVFileofWrongDelimeter_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaStateCodeFile(WRONG_STATE_CODE_FILE_DELIMETER);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCSVFileofWrongType_shouldReturnException()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            censusAnalyzerMain.loadIndiaStateCodeFile(WRONG_STATE_CODE_FILE_TYPE);
        }
        catch (CensusAnalyzerException e)
        {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM, e.type);
        }
    }
//---------------------------------------------------------------------------------------------

    @Test
    public void givenUsCensusData_shouldReturnCorrectRecords() {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            int usCencusCount = censusAnalyzerMain.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, usCencusCount);
        }
        catch (CensusAnalyzerException e){}
    }
    @Test
    public void whenGivenUSCensusCsvData_whenSortedOnPopulation_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            String sortedData = censusAnalyzerMain.sortUSJsonData(US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(4779736,  censusCSV[0].population);
        }
        catch (CensusAnalyzerException e){}
    }

    @Test
    public void whenGivenUSCensusCsvData_WhenSortedOnPopulationDen_ShouldReturnSortedResult() {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            String sortedData = censusAnalyzerMain.sortUSJsonData(US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(0.0, censusCSV[0].populationDensity, DELTA);
        }
        catch (CensusAnalyzerException e){}
    }

    @Test
    public void whenGivenUSCensusCsvData_whenSortedOnArea_ShouldRetuenSortedResult() {
        try
        {
            CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
            String sortedData = censusAnalyzerMain.sortUSJsonData(US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(135767.46, censusCSV[0].totalArea, DELTA);
        }
        catch (CensusAnalyzerException e){}
    }
}
