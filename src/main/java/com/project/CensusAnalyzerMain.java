package com.project;

//import com.bridgelabz.ICsvBuilder;

import com.bridgelabz.CsvBuilderfactory;
import com.bridgelabz.ICsvBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyzerMain
{
    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.loadIndiaCSVFile(reader, IndiaCensusCSV.class);
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
}

