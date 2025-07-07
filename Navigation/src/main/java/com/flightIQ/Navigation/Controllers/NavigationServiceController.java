package com.flightIQ.Navigation.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightIQ.Navigation.Service.NavigationService;
import com.flightIQ.Navigation.models.StateVector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1")
public class NavigationServiceController {

    @Autowired
    private NavigationService _navService;

    @GetMapping(value = "/getStateVectors", params = { "lamin", "lomin", "lamax", "lomax" })
    public StateVector[] getVectorsByBoundingBox(
            @RequestParam float lamin,
            @RequestParam float lomin,
            @RequestParam float lamax,
            @RequestParam float lomax) {
        return _navService.getStateVectors(lamin, lomin, lamax, lomax);
    }

    @GetMapping("/getStateVectorsUS")
    public StateVector[] getVectorsInUS() {
        return _navService.getStateVectorsUS();
    }

    // @GetMapping("/airportFrequencies")
    // public AirportFrequencies getAirportFrequencies(@RequestParam String ICAO) {
    //     return _navService.getAirportFrequencies(ICAO);
    // }
}