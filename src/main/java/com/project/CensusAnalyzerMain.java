package com.project;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyzerMain
{
    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
            Iterator<IndiaCensusCSV> csvIterator = this.loadIndiaCSVFile(reader, IndiaCensusCSV.class);
            return getRecordCount(csvIterator);
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCodeFile(String indiaStateCodeFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath));
            Iterator<IndiaStateCodeCsv> csvIterator = this.loadIndiaCSVFile(reader, IndiaStateCodeCsv.class);
            return getRecordCount(csvIterator);
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private  <E>Iterator <E> loadIndiaCSVFile(Reader reader, Class csvClass)
    {

        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass).withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
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

