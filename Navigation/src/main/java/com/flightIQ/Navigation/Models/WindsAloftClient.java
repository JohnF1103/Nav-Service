package com.flightIQ.Navigation.Models;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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
        try {
            String url = String.format("%s?airportCode=%s&altitude=%d", productServiceUrl, ICAO, cruiseALT);
            String response = restTemplate.getForObject(url, String.class); // Get raw string response
    
            if (response == null || response.isEmpty()) {
                System.out.println("Received empty or null response from winds aloft service.");
                return null;
            }
    
            WindAloft windAloft = WindAloft.fromString(response);
            return windAloft;
    
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("HTTP error while fetching winds aloft:");
            System.out.println("Status Code: " + ex.getStatusCode());
            System.out.println("Response Body: " + ex.getResponseBodyAsString());
            ex.printStackTrace();
        } catch (ResourceAccessException ex) {
            System.out.println("Network error while fetching winds aloft:");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected error while fetching winds aloft:");
            ex.printStackTrace();
        }
    
        return null;
    }
    
    

}
    
