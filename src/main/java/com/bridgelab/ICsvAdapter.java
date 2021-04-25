package com.bridgelab;

import java.util.Map;

public interface ICsvAdapter {
    public  <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCsvClass, String... csvFilepath)
                                                                    throws CensusAnalyzerException ;
}
