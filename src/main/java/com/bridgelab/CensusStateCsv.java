package com.bridgelab;

import java.util.HashMap;
import java.util.Map;

public class CensusStateCsv {
    public Map<String,CensusDAO> indiaUsCensusMap = new HashMap<>();

    public Map<String, CensusDAO> getIndiaUsCensusMap() {
        return indiaUsCensusMap;
    }

    public void setIndiaUsCensusMap(Map<String, CensusDAO> indiaUsCensusMap) {
        this.indiaUsCensusMap = indiaUsCensusMap;
    }

    public void putIndiaUsCensusMap(String key, CensusDAO val) {
        indiaUsCensusMap.put(key, val);
    }

    public String getindiaUsCensusMapKey(String key) {
        return String.valueOf(indiaUsCensusMap.get(key));
    }
}
