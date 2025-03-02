package com.flightIQ.Navigation.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.flightIQ.Navigation.Exceptions.AirportNotFoundException;
import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Service.Navigation_svc;


@RestController
@RequestMapping("/api/v1")
public class NavigationServiceController {

    @Autowired
    private Navigation_svc navservice;
    

    @GetMapping(value = "/getATISOfDestination")
    public ResponseEntity<String> getATISOfDestination(@RequestParam String airportCode) {
        try {
            return ResponseEntity.ok(navservice.GetATISOFDestination("42.212323","-72",airportCode));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value="/getAirportByIdent")
    public ResponseEntity<Airport> getAirportByIdent(@RequestParam String identCode) {
    	try {
    		if (identCode.length() != 3) {
    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDENT code must have 3 letters");
            }
    		
    		// Regex expression to check input contains only alphabets and numbers
        	if (!identCode.matches("[A-Za-z0-9]{3}")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ICAO code must contain only letters and numbers");
            }
    		
    		return ResponseEntity.ok(navservice.getAirportFromIDENT(identCode));
    	}
    	catch (ResponseStatusException e) {
        	// Re-throw ResponseStatusException to preserve the original status code
    		throw e;
    	}
    	catch (AirportNotFoundException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    	}
    	catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred processing your request");
    	}
    }
    
    @GetMapping(value="/getAirportByIcao")
    public ResponseEntity<Airport> getAirportByIcao(@RequestParam String icaoCode) {
        try {
        	if (icaoCode.length() != 4) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ICAO code must have 4 letters"); 
            }
        	
        	if (!icaoCode.matches("[A-Za-z0-9]{4}")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ICAO code must contain only letters and numbers");
            }
        	
            return ResponseEntity.ok(navservice.getAirportFromICAO(icaoCode));
        }
        catch (ResponseStatusException e) {
        	throw e;
        }
        catch (AirportNotFoundException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    	}
        catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred processing your request");
        }
    }
}