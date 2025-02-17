package com.flightIQ.Navigation.Service;



import org.springframework.stereotype.Service;


@Service
public class NavigationServiceImpl implements Navigation_svc {


    public static double haversine_formula(String latitudeStr, String longitudeStr, String DestAirportCode) {

        // Remove static keyword from declaration when finished testing!

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
        double distFromMyHouseToJFK = haversine_formula("42.72476", "-73.67652", "KJFK"); 
        System.out.println(distFromMyHouseToJFK);
    }

}
