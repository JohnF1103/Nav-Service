package com.flightIQ.Navigation.Service;



import org.springframework.stereotype.Service;


@Service
public class NavigationServiceImpl implements Navigation_svc {

    public int ComputeTrueCourse(int plottedCourse, String WindsAloftAtCruise, double lat, double lon){



        //adjust plotted course for wind correction
        

        //adjust for E/W variation

        int declination = 0;

        //adjust for compass error FOR STEER, maybe import table from 75200


        if (lat >= 0) {
            declination = (int) (10 - (lat / 10.0));
        } else {
            declination = (int) (-10 + (lat / 10.0));
        }


        return declination;
    }

    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub

        System.out.println(ComputeTrueCourse(0, DestAirportCode, 26.2473600,-80.1111272 ));
        String response = "";
        return response;
    }

}
