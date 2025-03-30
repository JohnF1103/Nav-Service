package com.flightIQ.Navigation.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

// Used to send the route in an XML format back to the client
@Getter
@Setter
@XmlRootElement(name = "flightRoute")
public class FlightRoute {
	private long routeId;
	private String departureICAOCode;
	private String arrivalICAOCode;
	private List<String> route = new ArrayList<>();
}
