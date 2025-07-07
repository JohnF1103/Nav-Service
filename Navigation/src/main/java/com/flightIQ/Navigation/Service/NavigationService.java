package com.flightIQ.Navigation.Service;

import com.flightIQ.Navigation.models.AirportFrequencies;
import com.flightIQ.Navigation.models.StateVector;



public interface NavigationService {
    // String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode);
    // AirportFrequencies getAirportFrequencies(String ICAO);
    StateVector[] getStateVectors(float lamin, float lomin, float lamax, float lomax);
    StateVector[] getStateVectorsUS();
}