
package com.flightIQ.Navigation.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;

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
    public String computeNavlog(String route, String aircraft, String cruiseALT, String TAS) {
        // TODO Auto-generated method stub
        

        // KIMM (26.2241,-81.3186) (26.2233,-80.4911) (26.2407,-80.2758) KPMP test data point
        //http://localhost:8080/api/v1/ComputeNavlog?route=KIMM%20(26.2241,-81.3186)%20(26.2233,-80.4911)%20(26.2407,-80.2758)%20KPMP&aircraft=yourAircraft&CruiseALT=4500&TAS=118
<<<<<<< HEAD

        String[] points = route.split(" ");
        
        
        for(String location: points){
        	// find if a fixx exists or not
        	// if fixx exists, get the latitude and longitude
        	// else pass latitude and longitude
        	System.out.println(location);

        }

    
        ArrayList<RouteNode> flightroute = new ArrayList<RouteNode>();
        
=======
    
        
        ArrayList<RouteNode> flightroute = prepareRouteObject(route);

        System.out.println(flightroute);
>>>>>>> integration

        return "";
    }


    /***************************************HELPER FUNCTIONS******************************************************/    


    public String getWindsAoft(){
        return "220@12";
    }
    public ArrayList<RouteNode> prepareRouteObject(String routeString) {
        HashMap<String, String> airportsSamplegraphDB = new HashMap<>();
        airportsSamplegraphDB.put("KPMP", "(26.2473,-80.1111)"); // Pompano Beach Airpark
        airportsSamplegraphDB.put("KIMM", "(26.4337,-81.4005)"); // Immokalee Regional Airport
        airportsSamplegraphDB.put("KPHK", "(26.7850,-80.6934)"); // Palm Beach County Glades Airport
        airportsSamplegraphDB.put("2IS", "(26.7352,-81.0511)"); // Airglades Airport
    
        ArrayList<RouteNode> routeNodes = new ArrayList<>();
        String[] routeParts = routeString.split(" ");
    
        for (int i = 0; i < routeParts.length - 1; i++) {
            double[] currentCoords = getCoordinates(routeParts[i], airportsSamplegraphDB);
            double[] nextCoords = getCoordinates(routeParts[i + 1], airportsSamplegraphDB);
    
            double bearing = computeBearing(currentCoords[0], currentCoords[1], nextCoords[0], nextCoords[1]);
            double distance = computeDistance(currentCoords[0], currentCoords[1], nextCoords[0], nextCoords[1]);
    
            routeNodes.add(new RouteNode(routeParts[i], bearing, distance));
        }
    
        // Add the last node with zero bearing and distance
        routeNodes.add(new RouteNode(routeParts[routeParts.length - 1], 0, 0));
    
        return routeNodes;
    }
    
    private double[] getCoordinates(String node, HashMap<String, String> db) {
        String coords = db.getOrDefault(node, node).replace("(", "").replace(")", "");
        String[] parts = coords.split(",");
        return new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])};
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
