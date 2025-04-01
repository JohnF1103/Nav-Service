
package com.flightIQ.Navigation.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.flightIQ.Navigation.DTO.RouteNode;

import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Repository.AirportRepository;
import com.flightIQ.Navigation.Exceptions.AirportNotFoundException;

@Service
public class NavigationServiceImpl implements Navigation_svc {
	
	@Autowired
	private AirportRepository airportRepository;

	public void AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
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

        String[] points = route.split(" ");

        for(String location: points){

            System.out.println("location: " +location);

        }

    
        
        ArrayList<RouteNode> flightroute = new ArrayList<RouteNode>();

        



        return "";
    }












    /***************************************HELPER FUNCTIONS******************************************************/    

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

    private double computeFuelBurnForLeg(Aircraft aircraft, double distance, double time){
        return 0;
    
    };

    private double computeTimeForLeg(double groundspeed, double distance ){

        return 0;
    }

}
