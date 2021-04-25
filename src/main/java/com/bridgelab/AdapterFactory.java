package com.bridgelab;

import com.bridgelabz.CsvBuilderException;
import com.bridgelabz.CsvBuilderfactory;
import com.bridgelabz.ICsvBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class AdapterFactory implements ICsvAdapter{
    //CensusAnalyzerMain censusAnalyzerMain = new CensusAnalyzerMain();
    //AdapterFactory adapterFactory = new AdapterFactory();
    public  Map<String,CensusDAO> indiaUsCensusCSVMap = new HashMap<>();


    @Override
    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCsvClass, String... csvFilepath) throws CensusAnalyzerException {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilepath[0]));
            ICsvBuilder csvBuilder = CsvBuilderfactory.createCsvBuilder();
            Iterator csvIterator = csvBuilder.loadIndiaCSVFile(reader, censusCsvClass);
            Iterable<E> csvIterable = () -> csvIterator;
            String censusClassName = censusCsvClass.getName();


            switch (censusClassName)
            {

                case "com.bridgelab.IndiaCensusCSV":
                    StreamSupport.stream(csvIterable.spliterator(),false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(csvState -> indiaUsCensusCSVMap.put(csvState.state, new CensusDAO(csvState)));
                    break;

                case "com.bridgelab.IndiaStateCodeCsv":
                    StreamSupport.stream(csvIterable.spliterator(),false)
                            .map(IndiaStateCodeCsv.class::cast)
                            .forEach(csvState -> indiaUsCensusCSVMap.put(csvState.stateName, new CensusDAO(csvState)));
                    break;

                case "com.bridgelab.USCensusCSV":
                    StreamSupport.stream(csvIterable.spliterator(),false)
                            .map(USCensusCSV.class::cast)
                            .forEach(csvState -> indiaUsCensusCSVMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
            }

        return indiaUsCensusCSVMap;
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
}
