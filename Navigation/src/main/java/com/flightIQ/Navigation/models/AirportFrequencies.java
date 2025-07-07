package com.flightIQ.Navigation.models;

import java.util.Collections;
import java.util.Map;

/**
 * {
 * "KENNEDY TWR":"119.1",
 * "ATIS":"115.1",
 * "NEW YORK DEP": "135.9",
 * "GND ALT":"121.65",
 * "NEW YORK RDO":"115.9",
 * "NEW YORK APPROACH (CAMRN)":"127.4",
 * "UNICOM":"122.95",
 * "NEW YORK APP (ROBER)":"125.7",
 * "CLNC DEL":"135.05",
 * "NEW YORK APPROACH (FINAL)":"132.4",
 * "GND":"121.9",
 * "TWR ALT":"123.9"
 * }
 */

public class AirportFrequencies {

    private final Map<String, Float> frequenciesMap;

    public AirportFrequencies() {
        frequenciesMap = Collections.emptyMap();
    }

    public AirportFrequencies(Map<String, Float> map) {
        frequenciesMap = map;
    }

    public Map<String, Float> getFrequenciesMap() {
        return frequenciesMap;
    }

    public boolean hasFrequency(String name) {
        return frequenciesMap.containsKey(name);
    }
}