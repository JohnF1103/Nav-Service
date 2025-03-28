package com.flightIQ.Navigation.Service;

import java.util.ArrayList;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Repository.AirportRepository;
import com.flightIQ.Navigation.Exceptions.AirportNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*; 


@Service
public class NavigationServiceImpl implements Navigation_svc {

    public String ComputeTrueCourseAndGroundsped(int plottedCourse, String WindsAloftAtCruise, double lat, double lon, int TAS){

        ArrayList<Integer> cars = new ArrayList<Integer>(); // Create an ArrayList object

        
        //adjust plotted course for wind correction

        int windHeading = Integer.parseInt(WindsAloftAtCruise.split("@")[0]);
        int windspeed = Integer.parseInt(WindsAloftAtCruise.split("@")[1]);

        double crosswindComponent = computeCrosswindComponent(windHeading, windspeed, plottedCourse);
        
        int WCA = (int) Math.round(Math.asin( (crosswindComponent) / TAS ));

        double groundSpeed = computeGroundSpeed(windHeading, windspeed, plottedCourse, TAS, WCA);
        //adjust for E/W variation


        /*

        ignore this func for now we will add later this week.


                int declination = 0;

                if (lat >= 0) {
                    declination = (int) (10 - (lat / 10.0));
                } else {
                    declination = (int) (-10 + (lat / 10.0));
                }

        */

        //adjust for compass error FOR STEER, maybe import table from 75200

        return "Data " + WCA+ " "+ groundSpeed ;
    }







    public static double computeCrosswindComponent(int windDirection, int windSpeed, int course) {

        // Placeholder
        double windAngle = Math.abs(windDirection - course);
        System.out.println(windDirection);

        // Actual implementation
        // Calculate the wind angle realtive to the aircraft heading/course
        int windToward = windDirection - 180; 
        if (windToward < 0) {
            windToward += 360; 
        }
        int noseWindAngle = windToward - course; 
        double crossWind = windSpeed * Math.sin(Math.toRadians(noseWindAngle)); 
        double headWind = windSpeed * Math.cos(Math.toRadians(noseWindAngle)); 

        System.out.println("Wind toward: " + windToward + " Nose wind angle: " + noseWindAngle + " Cross Wind: " + crossWind + " Head wind: " + headWind + "\n"); 

        // Calculate the wind correction angle (assume true airspeed (TAS) is 100kts)
        // double windCorrectionAngle = Math.asin()


        
        return windSpeed * Math.sin(Math.toRadians(windAngle)); 
    }







    public static double computeGroundSpeed(double airspeed, double windSpeed, double course, double windDirection, double windCorrectionAngle) {
        // Convert course and wind direction from degrees to radians
        double courseInRadians = Math.toRadians(course);
        double windDirectionInRadians = Math.toRadians(windDirection);
        
        // Calculate the angle between the course and the wind direction
        double angle = courseInRadians - windDirectionInRadians;
        
        // Calculate the wind component along the course
        double windComponentAlongCourse = windSpeed * Math.cos(angle);
        
        // Calculate the ground speed
        double groundSpeed = airspeed + windComponentAlongCourse;
        
        return groundSpeed;
    }



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



    @Autowired
	private AirportRepository airportRepository;

    public void AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }



    @Override
    public Airport getAirportFromIDENT(String identCode) {
        Airport airport = airportRepository.findByIdent(identCode)
                .orElseThrow(() -> new AirportNotFoundException("Airport does not exist with IDENT: " + identCode));

        return airport;
    }



    @Override
    public Airport getAirportFromICAO(String icaoCode) {
        Airport airport = airportRepository.findByIcao(icaoCode)
                          .orElseThrow(() -> new AirportNotFoundException("Airport does not exist with ICAO: " + icaoCode));

        return airport;
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