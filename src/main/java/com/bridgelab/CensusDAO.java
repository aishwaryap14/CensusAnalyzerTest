package com.bridgelab;

public class CensusDAO {

    private String state;
    private String stateCode;
    private double totalArea;
    private double populationdensity;
    private int population;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationdensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

   public CensusDAO(USCensusCSV usCensusCSV){
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        totalArea = usCensusCSV.totalArea;
        populationdensity = usCensusCSV.populationDensity;
        populationdensity = usCensusCSV.population;
   }

    public CensusDAO() { }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
