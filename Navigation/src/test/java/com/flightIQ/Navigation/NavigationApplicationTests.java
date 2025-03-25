package com.flightIQ.Navigation;

import com.flightIQ.Navigation.Service.NavigationServiceImpl;

public class NavigationApplicationTests {
    public static void main(String[] args) {
        NavigationServiceImpl navigationService = new NavigationServiceImpl();

        // Example call to ComputeTrueCourse
        int plottedCourse = 90;
        String windsAloft = "180@25";
        double lat = 26.2473600;
        double lon = -80.1111272;
        int trueAirspeed = 150;

        int trueCourse = navigationService.ComputeTrueCourse(plottedCourse, windsAloft, lat, lon, trueAirspeed);
        System.out.println("Computed True Course: " + trueCourse);

  
    }
}