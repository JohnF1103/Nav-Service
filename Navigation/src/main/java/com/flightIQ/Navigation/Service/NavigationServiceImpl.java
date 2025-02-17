package com.flightIQ.Navigation.Service;



import org.springframework.stereotype.Service;


@Service
public class NavigationServiceImpl implements Navigation_svc {


    public static double haversine_formula(String latitudeStr, String longitudeStr, String DestAirportCode) {

        // Remove static keyword from declaration when finished testing!

        double EARTH_RADIUS_MILES = 3958.8; 

        // Retrieve the destination airport's coordinates
        String dest_latStr; 
        String dest_longStr; 

        // Convert the coordinates to doubles
        double latitude = Double.parseDouble(latitudeStr); 
        double longitude = Double.parseDouble(longitudeStr); 
        double dest_latitude = 40.6446; // hard-coding JFK coordinates for now
        double dest_longitude = -73.7797; // hard-coding JFK coordinates for now

        double diff_latitude = dest_latitude - latitude; 
        double diff_longitude = dest_longitude - longitude; 

        // Apply haversine formula
        double a = Math.pow(Math.sin(diff_latitude / 2), 2) + 
                            Math.cos(latitude) * Math.cos(dest_latitude) * 
                            Math.pow(Math.sin(diff_longitude / 2), 2);

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); 

        double distance = EARTH_RADIUS_MILES * b; 


        return distance; // will change
    }



    @Override
    public String GetATISOFDestination(String latitude, String longitude, String DestAirportCode) {
        // TODO Auto-generated method stub

        String atisResponse = "";
        
        // if (haversine_formula(latitude, longitude, DestAirportCode) <= 10) {


        //     try {
        //         //make GET REQ using https://www.geeksforgeeks.org/spring-resttemplate/

        //         freqAPI_reponse = https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode=KPMP

        //         //parse out ATIS 

        //         atisResponse = //maybe global var in function. 

                
        //     } catch {

        //         throw exception

        //     } 

        // }


        return atisResponse;
    }


    public static void main(String[] args) {
        // FOR TESTING PURPOSES
        double distFromMyHouseToJFK = haversine_formula("40.86827", "-73.28612", "KJFK"); 
        System.out.println(distFromMyHouseToJFK);
    }

}
