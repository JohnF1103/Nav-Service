package com.flightIQ.Navigation.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "flightRoute")
public class FlightRoute {
	private long routeId;
	private String departureICAOCode;
	private String arrivalICAOCode;
	private List<String> route = new ArrayList<>();
}
