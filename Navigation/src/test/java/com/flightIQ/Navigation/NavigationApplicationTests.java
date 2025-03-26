package com.flightIQ.Navigation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
