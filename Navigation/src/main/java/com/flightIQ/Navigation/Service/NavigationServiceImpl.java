package com.flightIQ.Navigation.Service;


import org.springframework.stereotype.Service;


@Service
public class NavigationServiceImpl implements Navigation_svc {

    public int ComputeTrueCourse(int plottedCourse, String WindsAloftAtCruise, double lat, double lon, int TrueAirspeed){



        //adjust plotted course for wind correction
        int WindDir = Integer.parseInt(WindsAloftAtCruise.split("@")[0]);
        int WindSPeed = Integer.parseInt(WindsAloftAtCruise.split("@")[1]);
        int windAngle = Math.abs(WindDir -plottedCourse);

        //compute angle of abs(plotted course and wind direction for a heading of 180 the wind is coming FROM hdg 180)
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).


        //** Keep in mind that arcsin takes in radians NOT degrees. so take a degree value and use math.toradians to convert. */
       
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        //The wind correction angle can be calculated using the formula: WCA = arcsin (sin (Wind Angle in radians) * (Wind Speed / True Airspeed)).
        int WCA = 0;
        //adjust for E/W variation

        int declination = 0;

        //adjust for compass error FOR STEER, maybe import table from 75200


        if (lat >= 0) {
            declination = (int) (10 - (lat / 10.0));
        } else {
            declination = (int) (-10 + (lat / 10.0));
        }


        return plottedCourse - WCA;
    }

    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub

        System.out.println(ComputeTrueCourse(0, DestAirportCode, 26.2473600,-80.1111272, 0 ));
        String response = "";
        return response;
    }

}
