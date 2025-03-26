package com.flightIQ.Navigation.Service;



import java.util.ArrayList;

import org.springframework.stereotype.Service;


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

    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub

       // System.out.println(ComputeTrueCourse(0, DestAirportCode, 26.2473600,-80.1111272 ));
        String response = "";
        return response;
    }


    public static double computeCrosswindComponent(int windDirection, int windSpeed, int course) {


        double windAngle = Math.abs(windDirection - course);

        System.out.println(windDirection);
        

        return windSpeed * Math.sin(Math.toRadians(windAngle)); 
    }

    public static double computeGroundSpeed(double trueAirspeed, String windSpeed, double course, double windDirection, double windCorrectionAngle) {
        double courseRadians = Math.toRadians(course);
        double windDirectionRadians = Math.toRadians(windDirection);
        double windCorrectionAngleRadians = Math.toRadians(windCorrectionAngle);

        double groundSpeed = Math.sqrt(
            Math.pow(trueAirspeed, 2) + Math.pow(windSpeed, 2) -
            (2 * trueAirspeed * string * Math.cos(courseRadians - windDirectionRadians - windCorrectionAngleRadians))
        );

        return groundSpeed;
    }
}
