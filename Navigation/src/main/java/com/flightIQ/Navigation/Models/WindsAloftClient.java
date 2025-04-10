package com.flightIQ.Navigation.Models;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flightIQ.Navigation.DTO.WindAloft;



@Service
public class WindsAloftClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public WindsAloftClient(RestTemplate restTemplate, @Value("${Winds.service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
     
        
    }

    public WindAloft getWindsAloftByIcao(String ICAO, int cruiseALT) {
        return restTemplate.getForObject(String.format("%s?airportCode=%s&altitude=%d", productServiceUrl, ICAO, cruiseALT), WindAloft.class);
        
    }
}
    
