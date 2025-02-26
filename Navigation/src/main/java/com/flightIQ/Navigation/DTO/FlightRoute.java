package com.flightIQ.Navigation.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// Used to send the route in an XML format back to the client
@XmlRootElement(name = "flightRoute")
public class FlightRoute {
	private String departureICAOCode;
	private String arrivalICAOCode;
	
	// List of waypoints
	// Route with fixxes and origin and destination airport
	
	
}
