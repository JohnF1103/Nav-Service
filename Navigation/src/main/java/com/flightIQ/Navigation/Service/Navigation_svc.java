package com.flightIQ.Navigation.Service;

import java.util.ArrayList;
import java.util.Optional;

import com.flightIQ.Navigation.DTO.RouteNode;
import com.flightIQ.Navigation.Models.Airport;

public interface Navigation_svc {
	String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode);
	Airport getAirportFromIDENT(String ident);
	Airport getAirportFromICAO(String icaoCode);
    ArrayList<RouteNode> computeNavlog(String route, String aircraft, String cruiseALT, String TAS);
}
