
package com.flightIQ.Navigation.Service;
import com.flightIQ.Navigation.DTO.Aircraft;
import com.flightIQ.Navigation.DTO.AircraftDB;

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


    /********************************************
     * ENDPOINTS
     ******************************************************/
    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub

        // System.out.println(ComputeTrueCourse(0, DestAirportCode,
        // 26.2473600,-80.1111272 ));
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
    public String computeNavlog(String route, String aircraft, String cruiseALT, String TAS) {
        // TODO Auto-generated method stub

        // KIMM (26.2241,-81.3186) (26.2233,-80.4911) (26.2407,-80.2758) KPMP test data
        // point
        // http://localhost:8080/api/v1/ComputeNavlog?route=KIMM%20(26.2241,-81.3186)%20(26.2233,-80.4911)%20(26.2407,-80.2758)%20KPMP&aircraft=yourAircraft&CruiseALT=4500&TAS=118

        List<RouteNode> flightroute = prepareRouteObject(route);

        System.out.println(flightroute);
        System.out.println("APPLYIGN WINDS" + getWindsAoft());

        double totalFuelBurn = 0;
        double totalETE = 0;
        double totalDistance = 0; 

        for (int i = 0; i < flightroute.size(); i++) {
            RouteNode curr = flightroute.get(i);


            String phaseOfFlight = (i == 0) ? "CLB" : (i == flightroute.size() - 1) ? "DES" : "CRZ";

            int course = (int) curr.getBearing();

            System.out.println();

            double groundspeed = Double.parseDouble(
                    ComputeTrueCourseAndGroundsped(course, getWindsAoft(), Integer.parseInt(TAS)).split("-")[1]);
            double truecourse = Double.parseDouble(
                    ComputeTrueCourseAndGroundsped(course, getWindsAoft(), Integer.parseInt(TAS)).split("-")[0]);
            
            System.out.println("GRIUYNF SPEEED " + groundspeed);
            double dist = curr.getDistance();
            double timeForLeg = computeTimeForLeg(groundspeed, dist);

            double legFuelBurn = computeFuelBurnForLeg(getAircraftFromDB(aircraft), dist, timeForLeg, phaseOfFlight);

            totalETE += timeForLeg;
            totalFuelBurn += legFuelBurn;
            totalDistance += dist;

            System.out.println("TIME FOR LEG " + timeForLeg + "leg dist " + dist);
            System.out.println("FUEL BURNED " + legFuelBurn);
        }

        String formattedETE = formatTime(totalETE);

        System.out.println("TOTAL ETE " + totalETE);

        return "Distance " + truncate(totalDistance) + " Total ETE: " + formattedETE + ", Total Fuel Burn: " + truncate(totalFuelBurn + 1.5) + " gallons";
    }

    private String formatTime(double totalHours) {
        int hours = (int) totalHours;
        int minutes = (int) ((totalHours - hours) * 60);

        return String.format("%02d:%02d", hours, minutes);
    }

    private double truncate(double value) {
        return Math.floor(value * 100) / 100;
    }

    /***************************************
     * HELPER FUNCTIONS
     ******************************************************/

    public String getWindsAoft() {
        return "246@16";
    }


    
    public List<RouteNode> prepareRouteObject(String routeString) {
         // Route 1: KIMM (26.2241,-81.3186) (26.2233,-80.4911) (26.2407,-80.2758) KPMP test data point
        // Test Case 1: http://localhost:8080/api/v1/ComputeNavlog?route=KIMM%20(26.2241,-81.3186)%20(26.2233,-80.4911)%20(26.2407,-80.2758)%20KPMP&aircraft=yourAircraft&CruiseALT=4500&TAS=118
      
        String[] points = routeString.split(" ");
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

    public String ComputeTrueCourseAndGroundsped(int plottedCourse, String WindsAloftAtCruise, int TAS) {
        int windHeading = Integer.parseInt(WindsAloftAtCruise.split("@")[0]);
        int windspeed = Integer.parseInt(WindsAloftAtCruise.split("@")[1]);
    
        // Calculate the ground speed using the provided function
        double groundSpeed = computeGroundSpeed(TAS, windspeed, plottedCourse, windHeading);
    
        // Normalize the difference to be within -180 to 180 degrees.
        double angleDiff = ((windHeading - plottedCourse + 540) % 360) - 180;
        double angleDiffRad = Math.toRadians(angleDiff);
        
        // Compute sin(WCA) using the normalized angle difference
        double sinWCA = (windspeed * Math.sin(angleDiffRad)) / TAS;
        
        // Clip sinWCA to the valid range to avoid Math.asin throwing an exception
        sinWCA = Math.max(-1.0, Math.min(1.0, sinWCA));
        
        // Compute the wind correction angle (WCA)
        double WCA = Math.toDegrees(Math.asin(sinWCA));
        
        // Calculate the true course and normalize it to 0-360 degrees
        int truecourse = (int) ((plottedCourse + WCA + 360) % 360);
    
        return truecourse + "-" + groundSpeed;
    }
    

 

    public static double computeGroundSpeed(double airspeed, double windSpeed, double course, double windDirection) {
        // Convert course and wind direction from degrees to radians
        double courseInRadians = Math.toRadians(course);
        double windDirectionInRadians = Math.toRadians(windDirection);
        double angle = windDirectionInRadians - courseInRadians;
        double windComponent = windSpeed * Math.cos(angle);
        double groundSpeed = airspeed - windComponent; // Subtracting because windComponent is positive for headwind

        return groundSpeed;
    }

    private double computeFuelBurnForLeg(Aircraft aircraft, double distance, double time, String legType) {

        double fuelBurn = 0.0;
        double galsPerMin = aircraft.getCRZfuelBurn() / 60;

        switch (legType.toUpperCase()) {
            case "CLB":
                fuelBurn = (aircraft.getCLBFuelBurn() / 60) * time;
                break;
            case "CRZ":
                fuelBurn = (aircraft.getCRZfuelBurn() / 60) * time;
                break;
            case "DES":
                fuelBurn = (aircraft.getDescFuelbURN() / 60) * time;
                break;
            default:
                throw new IllegalArgumentException("Invalid leg type: " + legType);
        }

        return fuelBurn;
    }

    private double computeTimeForLeg(double groundspeed, double distance) {

        double nautical_miles_per_min = groundspeed / 60;
        return distance / nautical_miles_per_min;
    }

    public static Aircraft getAircraftFromDB(String ac) {
        return AircraftDB.getAircraftFromDB(ac);
    }

}
