package com.flightIQ.Navigation.DTO;

import java.util.HashMap;
import java.util.Map;

public class AircraftDB {
    private static Map<String, Aircraft> aircraftDB = new HashMap<>();

    static {
        aircraftDB.put("PA-28-151", new Aircraft("PA-28-151", 16.6, 10.0, 6.6, 1200, 800, 1500));
        aircraftDB.put("C172", new Aircraft("C172", 9.5, 8.0, 5.5, 1000, 700, 1450));
        aircraftDB.put("C182", new Aircraft("C182", 14.0, 12.0, 8.0, 1100, 750, 1700));
        aircraftDB.put("Boeing 737", new Aircraft("Boeing 737", 1000, 800, 600, 8000, 6000, 83000));
        aircraftDB.put("Airbus A320", new Aircraft("Airbus A320", 950, 750, 550, 7500, 5800, 84000));
        aircraftDB.put("Beechcraft G36 Bonanza", new Aircraft("Beechcraft G36 Bonanza", 16.5, 14.0, 10.5, 1400, 1200, 2200));
        aircraftDB.put("Cessna 162", new Aircraft("Cessna 162", 6.5, 5.5, 4.5, 900, 600, 830));
    }

    public static Aircraft getAircraftFromDB(String ac) {
        return aircraftDB.get(ac);
    }

}