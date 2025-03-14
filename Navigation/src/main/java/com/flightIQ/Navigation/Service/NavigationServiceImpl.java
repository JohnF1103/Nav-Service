package com.flightIQ.Navigation.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*; 


@Service
public class NavigationServiceImpl implements Navigation_svc {

    /* 
    public static void getATISTest() {
        // For testing purposes only

        String apiUrl = "https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode=KLAX";
        RestTemplate restTemplate = new RestTemplate(); 
        String apiResponseJSON = restTemplate.getForObject(apiUrl, String.class); 

        // parse out ATIS 
        String atisResponse = parseATIS(apiResponseJSON); 
        System.out.println(atisResponse);

    } */


    @Override
    public String GetATISOFDestination(String latitude, String longitude, String DestAirportCode) {
        // TODO Auto-generated method stub

        /*
            TODO FOR RUSSELL: When the database connection is working, write a simple SQL query that 
                                returns the latitiude and longitiude for DestAirportCode

                              When that is successful, modify the haversine formula function so that it takes
                                in the destination airport lat and long (replace hard-coded JFK coordinates)
        */ 

        String atisResponse = "";
        
        if (haversine_formula(latitude, longitude, DestAirportCode) <= 10) {
            try {
                // Send get request to url to retrieve JSON object
                String apiUrl = "https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode={station}";
                String endpoint = apiUrl.replace("{station}", DestAirportCode);
                RestTemplate restTemplate = new RestTemplate(); 
                String apiResponseJSON = restTemplate.getForObject(endpoint, String.class); 

                // parse out ATIS 
                atisResponse = parseATIS(apiResponseJSON); 

            } catch (Exception e) {
                System.out.printf("Error retrieving Airport ATIS Code."); 
            } 
        }

        return atisResponse;
    }



    public String parseATIS(String apiResponseJSON) {
        /*
         * Add static if testing
         * Remove static if done testing
         */

        String atisOutput = ""; 

        JSONObject result = new JSONObject(apiResponseJSON);

        if (result.has("ATIS") && !result.isNull("ATIS")) {
            Object atisCodeObj = result.get("ATIS"); 
            atisOutput += "ATIS: " + atisCodeObj; 
        }

        return atisOutput; 
    }



    public double haversine_formula(String latitudeStr, String longitudeStr, String DestAirportCode) {
        /*
         * Add static if testing
         * Remove static if done testing
         */

        double earthRadiusNauticalMiles = 3443.92; 

        // Retrieve the destination airport's coordinates
        String dest_latStr; 
        String dest_longStr; 

        // Convert the coordinates to doubles
        double latitude = Math.toRadians(Double.parseDouble(latitudeStr)); 
        double longitude = Math.toRadians(Double.parseDouble(longitudeStr)); 
        double dest_latitude = Math.toRadians(40.6446); // hard-coding JFK coordinates for now
        double dest_longitude = Math.toRadians(-73.7797); // hard-coding JFK coordinates for now

        double diff_latitude = dest_latitude - latitude; 
        double diff_longitude = dest_longitude - longitude; 

        // Apply haversine formula
        double a = Math.pow(Math.sin(diff_latitude / 2), 2) + 
                            Math.cos(latitude) * Math.cos(dest_latitude) * 
                            Math.pow(Math.sin(diff_longitude / 2), 2);

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); 

        double distance = earthRadiusNauticalMiles * b; 

        return distance; 
    }



    /* 
    public static void main(String[] args) {
        // FOR TESTING PURPOSES
        double distFromMyHouseToJFK = haversine_formula("42.72476", "-73.67652", "KJFK"); 
        System.out.println(distFromMyHouseToJFK);

        String JSONResult = GetATISOFDestination("42.72476", "-73.67652", "KJFK"); 

        getATISTest(); 
    } */

}
