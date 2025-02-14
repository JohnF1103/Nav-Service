package com.flightIQ.Navigation.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}