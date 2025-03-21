package com.flightIQ.Navigation.Exceptions;

public class AirportNotFoundException extends RuntimeException {
	public AirportNotFoundException(String message) {
		super(message);
	}
}
