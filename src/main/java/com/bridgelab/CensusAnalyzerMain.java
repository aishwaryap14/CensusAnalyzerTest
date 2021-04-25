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
import java.util.stream.Collectors;

public class CensusAnalyzerMain
{
    AdapterFactory adapterFactory = new AdapterFactory();
    CensusDAO indiaCensusDAO = new CensusDAO();
    List<CensusDAO> csvList = null;
    List<IndiaStateCodeCsv> stateCodeCsvList = null;

    Map<String,CensusDAO> censusCSVMap = new HashMap<>();

    public CensusAnalyzerMain()
    {
        this.csvList = new ArrayList<CensusDAO>();
        //this.stateCodeCsvList = new ArrayList<IndiaStateCodeCsv>();

    }


    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        censusCSVMap = adapterFactory.loadCensusData(IndiaCensusCSV.class, indiaCensusCsvFilePath);
        return censusCSVMap.size();
    }

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyzerException
    {
        censusCSVMap = adapterFactory.loadCensusData(USCensusCSV.class, usCensusCsvFilePath);
        return censusCSVMap.size();
    }

    public int loadIndiaStateCodeFile(String indiaStateCodeFilePath) throws CensusAnalyzerException
    {
        censusCSVMap = adapterFactory.loadCensusData(IndiaStateCodeCsv.class, indiaStateCodeFilePath);
        return censusCSVMap.size();
    }



    public String sortIndiaJsonData(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCsvJsonData(indiaCensusCsvFilePath);
    }

    public String sortUSJsonData(String usCensusCsvFilePath) throws CensusAnalyzerException
    {
        return this.loadCsvJsonData(usCensusCsvFilePath);
    }



    public <E>String sortIndiaUsJsonData(String csvpath) throws CensusAnalyzerException{
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvpath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            List<E> censusDAOList = csvBuilder.loadIndiaCSVFileList(reader, CensusDAO.class);
            System.out.println(censusDAOList);
            Comparator<CensusDAO> censusDAOComparator = Comparator
                    .comparingDouble(CensusDAO::getPopulation)
                    .thenComparingDouble(CensusDAO::getPopulationdensity);
            List<E> sortedDaoList = censusDAOList.stream()
                    .sorted((Comparator<? super E>) censusDAOComparator.reversed())
                    .collect(Collectors.toList());
            String sortedJsonList = new Gson().toJson(sortedDaoList);
            return sortedJsonList;
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

    private void sort(List<CensusDAO> csvList)
    {
        csvList.sort((CensusDAO state1, CensusDAO state2 )-> state1.getState().compareTo(state2.getState()));
    }


}

