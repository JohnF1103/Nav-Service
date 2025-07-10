package com.flightIQ.Navigation.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.flightIQ.Navigation.DTO.RouteNode;
import com.flightIQ.Navigation.DTO.StateVector;
import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Models.FIXX;

public interface Navigation_svc {
	String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode);
	Airport getAirportFromIDENT(String ident);
	Airport getAirportFromICAO(String icaoCode);
	FIXX getFIXXFromId(String fixxId);
	String computeNavlog(String route, String aircraft, String cruiseALT, String TAS);

	StateVector[] getStateVectors(float lamin, float lomin, float lamax, float lomax);
	StateVector[] getStateVectorsUS();
}

