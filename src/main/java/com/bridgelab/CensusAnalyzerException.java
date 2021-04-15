package com.bridgelab;

public class CensusAnalyzerException extends Exception
{


    enum ExceptionType
    {
        CENSUS_FILE_PROBLEM, CENSUS_CONTENT_PROBLEM,UNABLE_TO_PARSE,NO_DATA_FOUND
    }

    ExceptionType type;

    public CensusAnalyzerException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }

//    public CensusAnalyzerException(String message, ExceptionType type, Throwable cause)
//    {
//        super(message, cause);
//        this.type = type;
//    }
}
