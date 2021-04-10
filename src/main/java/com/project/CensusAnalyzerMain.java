package com.project;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyzerMain {
    public int loadIndiaCensusFile(String indiaCensusCsvFilePath) throws CensusAnalyzerException
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class).withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> csvIterator = csvToBean.iterator();
            int numOfEntries = 0;
            while (csvIterator.hasNext())
            {
                numOfEntries++;
                IndiaCensusCSV indiaCensusCSV = csvIterator.next();
            }
            return numOfEntries;
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCodeFile(String indiaStateCodeFilePath) throws CensusAnalyzerException {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath));
            CsvToBeanBuilder<IndiaStateCodeCsv> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCodeCsv.class).withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCodeCsv> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCodeCsv> csvIterator = csvToBean.iterator();
            int numOfEntries = 0;
            while (csvIterator.hasNext())
            {
                numOfEntries++;
                IndiaStateCodeCsv stateCodeCsv = csvIterator.next();
            }
            return numOfEntries;
        }
        catch (IOException e)
        {
            throw new CensusAnalyzerException(e.getMessage(),
                    CensusAnalyzerException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    }

