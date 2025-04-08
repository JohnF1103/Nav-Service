
package com.flightIQ.Navigation.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import com.flightIQ.Navigation.DTO.RouteNode;

import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Models.FIXX;
import com.flightIQ.Navigation.Repository.AirportRepository;
import com.flightIQ.Navigation.Repository.FIXXRepository;
import com.flightIQ.Navigation.Exceptions.AirportNotFoundException;
import com.flightIQ.Navigation.Exceptions.FixxNotFoundException;

@Service
public class NavigationServiceImpl implements Navigation_svc {

	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private FIXXRepository fixxRepository;

	public void AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
	
	public void FIXXService(FIXXRepository fixxRepository) {
		this.fixxRepository = fixxRepository;
	}
	
	
/********************************************ENDPOINTS******************************************************/    
    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub
        
        //if distance formula (x,y) , DestAirportCode <= 10{


//            try{
//                //make GET REQ using https://www.geeksforgeeks.org/spring-resttemplate/
//
//                freqAPI_reponse = https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode=KPMP
//
//                //parse out ATIS 
//
//                String ATIS_response = //maybe global var in function. 
//
//                response = ATIS_response;
//                
//            }catch{
//
//                trow exception
//
//            }
//
//        }else{
//
//            response = ""
//        }
       // System.out.println(ComputeTrueCourse(0, DestAirportCode, 26.2473600,-80.1111272 ));
        String response = "";
        return response;
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
    
	@Override
	public FIXX getFIXXFromId(String fixxId) {
		FIXX fixx = fixxRepository.findByFixxId(fixxId)				
					.orElseGet(() -> {
						if (fixxId.charAt(0) == '(' && fixxId.charAt(fixxId.length() - 1) == ')') {
							FIXX fixx1 = new FIXX();
							fixx1.setFixxId(fixxId);
		            		String[] formattedCoordinates = (fixxId.substring(1,fixxId.length() - 1)).split(",");
		            		fixx1.setLatitude(Double.parseDouble(formattedCoordinates[0]));
		            		fixx1.setLongitude(Double.parseDouble(formattedCoordinates[1]));
		            		return fixx1;
						}
						else {
							throw new FixxNotFoundException("FIXX not found with ID: " + fixxId);
						}
					});
		
		return fixx;
	}

    @Override
    public List<RouteNode> computeNavlog(String route, String aircraft, String cruiseALT, String TAS) {
        // Route 1: KIMM (26.2241,-81.3186) (26.2233,-80.4911) (26.2407,-80.2758) KPMP test data point
        // Test Case 1: http://localhost:8080/api/v1/ComputeNavlog?route=KIMM%20(26.2241,-81.3186)%20(26.2233,-80.4911)%20(26.2407,-80.2758)%20KPMP&aircraft=yourAircraft&CruiseALT=4500&TAS=118
        String[] points = route.split(" ");
        ArrayList<RouteNode> flightRoute = new ArrayList<RouteNode>();
        
        // Case: No Fixxes in route; ex: KJFK -> KALB
        if (points.length == 2) {
        	Airport departureAirport = getAirportFromICAO(points[0]);
    		Airport arrivalAirport = getAirportFromICAO(points[1]);
        	
    		
        	double bearing = computeBearing(departureAirport.getLatitude(), departureAirport.getLongitude(), arrivalAirport.getLatitude(), arrivalAirport.getLongitude());
            double distance = computeDistance(departureAirport.getLatitude(), departureAirport.getLongitude(), arrivalAirport.getLatitude(), arrivalAirport.getLongitude());
    		
    		flightRoute.add(new RouteNode(departureAirport.getIcao(),bearing,distance));
    		flightRoute.add(new RouteNode(arrivalAirport.getIcao(), 0.0, 0.0));
    		return flightRoute;
        }
        
        
        for (int i = 0; i <= points.length - 2; i++) {
        	System.out.println(points[i]);
        	if (i == 0) {
        		Airport departureAirport = getAirportFromICAO(points[i]);
        		FIXX fixx = getFIXXFromId(points[i + 1]);
            	
        		
            	double bearing = computeBearing(departureAirport.getLatitude(), departureAirport.getLongitude(), fixx.getLatitude(), fixx.getLongitude());
                double distance = computeDistance(departureAirport.getLatitude(), departureAirport.getLongitude(), fixx.getLatitude(), fixx.getLongitude());
        		
        		flightRoute.add(new RouteNode(departureAirport.getIcao(),bearing,distance));
        	}
        	else if (i == points.length - 2) {
        		FIXX fixx = getFIXXFromId(points[i]);
        		Airport arrivalAirport = getAirportFromICAO(points[i + 1]);
            	
            	
              	double bearing = computeBearing(fixx.getLatitude(), fixx.getLongitude(), arrivalAirport.getLatitude(), arrivalAirport.getLongitude());
                double distance = computeDistance(fixx.getLatitude(), fixx.getLongitude(), arrivalAirport.getLatitude(), arrivalAirport.getLongitude());
        		
        		flightRoute.add(new RouteNode(fixx.getFixxId(),bearing,distance));
        	}
        	else {
        		FIXX fixx1 = getFIXXFromId(points[i]);
        		FIXX fixx2 = getFIXXFromId(points[i + 1]);
            		
            	double bearing = computeBearing(fixx1.getLatitude(), fixx1.getLongitude(), fixx2.getLatitude(), fixx2.getLongitude());
                double distance = computeDistance(fixx1.getLatitude(), fixx1.getLongitude(), fixx2.getLatitude(), fixx2.getLongitude());
        		
        		flightRoute.add(new RouteNode(fixx1.getFixxId(),bearing,distance));
        	}
        }
  
        Airport arrivalAirport = getAirportFromICAO(points[points.length - 1]);
        flightRoute.add(new RouteNode(arrivalAirport.getIcao(),0.0,0.0));
        System.out.println(flightRoute.toString());
        
        return (List<RouteNode>) flightRoute;
    }


    /***************************************HELPER FUNCTIONS******************************************************/    


    public String getWindsAoft(){
        return "220@12";
    }
    
    private double computeBearing(double lat1, double lon1, double lat2, double lon2) {
        double dLon = Math.toRadians(lon2 - lon1);
        double y = Math.sin(dLon) * Math.cos(Math.toRadians(lat2));
        double x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) -
                   Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(dLon);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }
    
    private double computeDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 3440; // Radius of the Earth in nautical miles
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in nautical miles
    }
    

    public String ComputeTrueCourseAndGroundsped(int plottedCourse, String WindsAloftAtCruise, double lat, double lon, int TAS){

        
        //adjust plotted course for wind correction

        int windHeading = Integer.parseInt(WindsAloftAtCruise.split("@")[0]);
        int windspeed = Integer.parseInt(WindsAloftAtCruise.split("@")[1]);

        double crosswindComponent = computeCrosswindComponent(windHeading, windspeed, plottedCourse);
        
      
        double groundSpeed = computeGroundSpeed(TAS , windspeed, plottedCourse, windHeading);
        //adjust for E/W variation


        double windDirectionRad = Math.toRadians(windHeading);
        double plottedCourseRad = Math.toRadians(plottedCourse);

        // Calculate the wind correction angle (WCA)
        double sinWCA = (windspeed * Math.sin(windDirectionRad - plottedCourseRad)) / TAS;
        double WCA = Math.toDegrees(Math.asin(sinWCA));
        

        System.out.println("WCA->" +  WCA +" "+ TAS +" " +crosswindComponent);
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


        double windAngle = Math.abs(windDirection - course);        

        return windSpeed * Math.sin(Math.toRadians(windAngle)); 
    }

    public static double computeGroundSpeed(double airspeed, double windSpeed, double course, double windDirection) {
        // Convert course and wind direction from degrees to radians
        double courseInRadians = Math.toRadians(course);
        double windDirectionInRadians = Math.toRadians(windDirection);
        
        // Calculate the angle between the course and the wind direction
        double angle = windDirectionInRadians - courseInRadians;
        
        // Calculate the headwind/tailwind component
        double windComponent = windSpeed * Math.cos(angle);
        
        // Calculate the ground speed
        double groundSpeed = airspeed - windComponent; // Subtracting because windComponent is positive for headwind
        
        return groundSpeed;
    }

    private double computeFuelBurnForLeg(Aircraft aircraft, double distance, double time, String legType){

        double fuelBurn = 0.0;
        double galsPerMin = aircraft.getCRZfuelBurn() / 60;
    
        switch (legType.toUpperCase()) {
            case "CLB":
                fuelBurn = aircraft.getCLBFuelBurn() * time;
                break;
            case "CRZ":
                fuelBurn = aircraft.getCRZfuelBurn() * time;
                break;
            case "DES":
                fuelBurn = aircraft.getDescFuelbURN() * time;
                break;
            default:
                throw new IllegalArgumentException("Invalid leg type: " + legType);
        }
    
        return fuelBurn;
    }
    

    private double computeTimeForLeg(double groundspeed, double distance ){

        double nautical_miles_per_min = groundspeed / 60;
        return distance * nautical_miles_per_min;
    }

}
