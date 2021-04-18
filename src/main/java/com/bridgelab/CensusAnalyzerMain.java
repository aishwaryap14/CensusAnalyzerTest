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
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
           Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.loadIndiaCSVFile(reader, IndiaCensusCSV.class);
           while (csvFileIterator.hasNext())
           {
               this.csvList.add(new CensusDAO(csvFileIterator.next()));
           }
            return this.csvList.size();
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

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyzerException {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(usCensusCsvFilePath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            Iterator<USCensusCSV> csvFileIterator = csvBuilder.loadIndiaCSVFile(reader, USCensusCSV.class);
            while (csvFileIterator.hasNext())
            {
                this.csvList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.csvList.size();
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


    public String getStateWiseSortedCsvData() throws CensusAnalyzerException{
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

