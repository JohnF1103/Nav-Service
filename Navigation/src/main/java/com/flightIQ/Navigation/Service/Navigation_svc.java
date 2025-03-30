package com.flightIQ.Navigation.Service;

import java.util.Optional;
import com.flightIQ.Navigation.Models.Airport;

public interface Navigation_svc {
	String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode);
	Airport getAirportFromIDENT(String ident);
	Airport getAirportFromICAO(String icaoCode);
}
