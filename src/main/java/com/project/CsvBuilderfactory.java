package com.project;

public class CsvBuilderfactory {
    public  static ICsvBuilder createCsvBuilder() {
        return new OpenCsvBuilder();
    }
}
