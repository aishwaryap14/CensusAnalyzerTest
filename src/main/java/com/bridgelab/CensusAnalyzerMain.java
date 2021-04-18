package com.bridgelab;


import com.bridgelabz.CsvBuilderException;
import com.bridgelabz.CsvBuilderfactory;
import com.bridgelabz.ICsvBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyzerMain
{
    CensusDAO indiaCensusDAO = new CensusDAO();
    List<CensusDAO> csvList = null;
    List<IndiaStateCodeCsv> stateCodeCsvList = null;
    Map<String,IndiaStateCodeCsv> indiaCensusDAOMap = null;
    public CensusAnalyzerMain()
    {
        this.csvList = new ArrayList<CensusDAO>();
        this.stateCodeCsvList = new ArrayList<IndiaStateCodeCsv>();
        this.indiaCensusDAOMap = new HashMap<>();
        stateCodeCsvList.forEach(e ->indiaCensusDAOMap.put(indiaCensusDAO.getState(),e));
    }


    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCensusData(indiaCensusCsvFilePath, IndiaCensusCSV.class);
    }

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
    }

    public String sortIndiaJsonData(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCsvJsonData(indiaCensusCsvFilePath);
    }
    public String sortUSJsonData(String usCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCsvJsonData(usCensusCsvFilePath);
    }

    private <E>String loadCsvJsonData(String CensusCsvFilePath) throws CensusAnalyzerException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(CensusCsvFilePath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            List<CensusDAO> csvList = csvBuilder.loadIndiaCSVFileList(reader, CensusDAO.class);
            if(csvList == null || csvList.size() == 0)
            {
                throw new CensusAnalyzerException("No data found",CensusAnalyzerException.ExceptionType.NO_DATA_FOUND);
            }
            this.sort(csvList);
            String sortedCensusJson = new Gson().toJson(csvList);
            return  sortedCensusJson;
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (CsvBuilderException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.UNABLE_TO_PARSE);
        }

    }

    public int loadIndiaStateCodeFile(String indiaStateCodeFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath));

            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            Iterator<IndiaStateCodeCsv> csvIterator = csvBuilder.loadIndiaCSVFile(reader, IndiaStateCodeCsv.class);
            Iterable<IndiaStateCodeCsv> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                         .forEach(csvState -> indiaCensusDAOMap.get(csvState.stateName).stateCode = csvState.stateCode);
            return indiaCensusDAOMap.size();
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM);
        }
        catch (CsvBuilderException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E>int loadCensusData (String csvFilepath, Class<E> censusCsvClass) throws CensusAnalyzerException {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilepath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            Iterator<E> csvFileIterator = csvBuilder.loadIndiaCSVFile(reader, censusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .map(IndiaCensusCSV.class::cast)
                    .forEach(censusCSV -> indiaCensusDAOMap.put(censusCSV.state, new IndiaStateCodeCsv(censusCSV)));
            return indiaCensusDAOMap.size();
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_CONTENT_PROBLEM);
        }
        catch (CsvBuilderException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.UNABLE_TO_PARSE);
        }

    }


    public String getSortedCsvData(String usCensusCsvFilePath) throws CensusAnalyzerException
    {

        if(csvList == null || csvList.size() == 0)
        {
            throw new CensusAnalyzerException("No data found",CensusAnalyzerException.ExceptionType.NO_DATA_FOUND);
        }
        this.sort(csvList);
        String sortedCensusJson = new Gson().toJson(csvList);
        return  sortedCensusJson;
    }

      private void sort(List<CensusDAO> csvList)
    {
        csvList.sort((CensusDAO state1, CensusDAO state2 )-> state1.getState().compareTo(state2.getState()));
    }


}

