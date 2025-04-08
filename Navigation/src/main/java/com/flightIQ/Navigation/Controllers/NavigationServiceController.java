package com.flightIQ.Navigation.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.flightIQ.Navigation.DTO.RouteNode;
import com.flightIQ.Navigation.Exceptions.AirportNotFoundException;
import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Service.Navigation_svc;
import com.flightIQ.Navigation.Exceptions.BadRequestException;


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
    	if (identCode.length() != 3) {
            throw new BadRequestException("IDENT code must have 3 letters");
        }

        // Regex expression to check input contains only alphabets and numbers
        if (!identCode.matches("[A-Za-z0-9]{3}")) {
            throw new BadRequestException("IDENT code must contain only letters and numbers");
        }

        return ResponseEntity.ok(navservice.getAirportFromIDENT(identCode));
    }
    
    @GetMapping(value="/getAirportByIcao")
    public ResponseEntity<Airport> getAirportByIcao(@RequestParam String icaoCode) {
    	if (icaoCode.length() != 4) {
            throw new BadRequestException("ICAO code must have 4 letters");
        }

        if (!icaoCode.matches("[A-Za-z0-9]{4}")) {
            throw new BadRequestException("ICAO code must contain only letters and numbers");
        }

        return ResponseEntity.ok(navservice.getAirportFromICAO(icaoCode));
    }
        
    @GetMapping(value = "/ComputeNavlog")
    public ResponseEntity<String> computeNavlog(@RequestParam String route, @RequestParam String aircraft, @RequestParam String CruiseALT, @RequestParam String TAS) {
       return ResponseEntity.ok(navservice.computeNavlog(route, aircraft, CruiseALT, TAS));
    }
}