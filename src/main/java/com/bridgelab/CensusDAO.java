package com.bridgelab;

public class CensusDAO {
    public String state;
    public String stateCode;
    private double totalArea;
    private double populationdensity;
    private int population;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationdensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCodeCsv indiaStateCodeCsv)
    {
        state = indiaStateCodeCsv.stateName;
        stateCode = indiaStateCodeCsv.stateCode;

    }

   public CensusDAO(USCensusCSV usCensusCSV){
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        totalArea = usCensusCSV.totalArea;
        populationdensity = usCensusCSV.populationDensity;
        population = usCensusCSV.population;
   }

    public CensusDAO() { }

    public CensusDAO(CensusDAO csvState) {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPopulationdensity() {
        return populationdensity;
    }

    public void setPopulationdensity(double populationdensity) {
        this.populationdensity = populationdensity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
