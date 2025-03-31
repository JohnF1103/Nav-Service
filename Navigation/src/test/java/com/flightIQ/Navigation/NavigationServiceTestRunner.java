package com.flightIQ.Navigation;

import com.flightIQ.Navigation.Service.NavigationServiceImpl;

public class NavigationServiceTestRunner {

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testComputeGroundSpeedWithNoWind();
        testComputeGroundSpeedWithTailwind();
        testComputeGroundSpeedWithHeadwind();
        testComputeGroundSpeedWithCrosswind();
        testComputeGroundSpeedWithComplexWindScenario1();
        testComputeGroundSpeedWithComplexWindScenario2();
    }

    public static void testComputeGroundSpeedWithNoWind() {
        double airspeed = 150.0;
        double windSpeed = 0.0;
        double course = 0.0;
        double windDirection = 0.0;
        double windCorrectionAngle = 0.0;
        
        double expectedGroundSpeed = 150.0;
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test No Wind: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void testComputeGroundSpeedWithTailwind() {
        double airspeed = 150.0;
        double windSpeed = 30.0;
        double course = 0.0;
        double windDirection = 0.0;
        double windCorrectionAngle = 0.0;
        
        double expectedGroundSpeed = 180.0;
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test Tailwind: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void testComputeGroundSpeedWithHeadwind() {
        double airspeed = 150.0;
        double windSpeed = 30.0;
        double course = 180.0;
        double windDirection = 0.0;
        double windCorrectionAngle = 0.0;
        
        double expectedGroundSpeed = 120.0;
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test Headwind: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void testComputeGroundSpeedWithCrosswind() {
        double airspeed = 150.0;
        double windSpeed = 30.0;
        double course = 90.0;
        double windDirection = 0.0;
        double windCorrectionAngle = 24.0;
        
        double expectedGroundSpeed = Math.sqrt(Math.pow(150.0, 2) + Math.pow(30.0, 2));
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test Crosswind: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void testComputeGroundSpeedWithComplexWindScenario1() {
        double airspeed = 150.0;
        double windSpeed = 40.0;
        double course = 45.0;
        double windDirection = 90.0;
        double windCorrectionAngle = 22.0;
        
        double expectedGroundSpeed = 160.0; // Example value, should be calculated using E6B
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test Complex Wind Scenario 1: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void testComputeGroundSpeedWithComplexWindScenario2() {
        double airspeed = 150.0;
        double windSpeed = 50.0;
        double course = 135.0;
        double windDirection = 270.0;
        double windCorrectionAngle = 15.0;
        
        double expectedGroundSpeed = 140.0; // Example value, should be calculated using E6B
        double actualGroundSpeed = NavigationServiceImpl.computeGroundSpeed(airspeed, windSpeed, course, windDirection, windCorrectionAngle);
        
        System.out.println("Test Complex Wind Scenario 2: Expected Ground Speed = " + expectedGroundSpeed + ", Actual Ground Speed = " + actualGroundSpeed);
        assertEquals(expectedGroundSpeed, actualGroundSpeed, 0.001);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            System.out.println("Test failed: Expected = " + expected + ", Actual = " + actual);
        } else {
            System.out.println("Test passed: Expected = " + expected + ", Actual = " + actual);
        }
    }
}