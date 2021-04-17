package com.bridgelab;



import com.bridgelabz.CsvBuilderException;
import com.bridgelabz.CsvBuilderfactory;
import com.bridgelabz.ICsvBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class CensusAnalyzerMain
{
    List<IndiaCensusCSV> csvList = null;
    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            csvList = csvBuilder.loadIndiaCSVFileList(reader, IndiaCensusCSV.class);
            return csvList.size();
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
            return getRecordCount(csvIterator);
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



    private <E>int getRecordCount (Iterator<E> iterator)
    {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = 0;
        while (iterator.hasNext())
        {
            numOfEntries++;
            E enext = iterator.next();
        }
        return numOfEntries;
    }

    public String getStateWiseSortedCsvData() throws CensusAnalyzerException{
        if(csvList == null || csvList.size() == 0)
        {
            throw new CensusAnalyzerException("No data found",CensusAnalyzerException.ExceptionType.NO_DATA_FOUND);
        }
        //Comparator<IndiaCensusCSV>csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvList);
        String sortedCensusJson = new Gson().toJson(csvList);
        return  sortedCensusJson;
    }

      private void sort(List<IndiaCensusCSV> csvList)
    {
        csvList.sort((IndiaCensusCSV state1, IndiaCensusCSV state2 )-> state1.getState().compareTo(state2.getState()));
    }
}

