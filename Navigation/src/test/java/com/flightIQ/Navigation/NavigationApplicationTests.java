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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationApplicationTests {

    public void testComputeCrosswindComponent() {
        NavigationServiceImpl service = new NavigationServiceImpl();

        double result1 = NavigationServiceImpl.computeCrosswindComponent(45, 20, 90);
        assertEquals(14.14, result1, 0.01);
        System.out.println("Test passed: computeCrosswindComponent (45, 20, 90)");

        double result2 = NavigationServiceImpl.computeCrosswindComponent(90, 30, 180);
        assertEquals(30.0, result2, 0.01);
        System.out.println("Test passed: computeCrosswindComponent (90, 30, 180)");

        double result3 = NavigationServiceImpl.computeCrosswindComponent(270, 25, 360);
        assertEquals(25.0, result3, 0.01);
        System.out.println("Test passed: computeCrosswindComponent (270, 25, 360)");

        double result4 = NavigationServiceImpl.computeCrosswindComponent(330, 12, 70);
        assertEquals(11.82, result4, 0.01);
        System.out.println("Test passed: computeCrosswindComponent (330, 12, 70)");
    }


    public void testComputeGroundSpeed() {
        NavigationServiceImpl service = new NavigationServiceImpl();

        // double result1 = NavigationServiceImpl.computeGroundSpeed(45, 20, 90, 150);
        // assertEquals(163.0, result1,2);
        // System.out.println("Test passed: computeGroundSpeed (45, 20, 90, 150)");

        // double result2 = NavigationServiceImpl.computeGroundSpeed(90, 30, 180, 200);
        // assertEquals(199.0, result2, 1);
        // System.out.println("Test passed: computeGroundSpeed (90, 30, 180, 200)");

        double result3 = NavigationServiceImpl.computeGroundSpeed(270, 25, 360, 220);
        assertEquals(195.0, result3, 1);
        System.out.println("Test passed: computeGroundSpeed (270, 25, 360, 220)");

        // double result4 = NavigationServiceImpl.computeGroundSpeed(360, 270, 25, 220);
        // assertEquals(245.0, result4, 1);
        // System.out.println("Test passed: computeGroundSpeed (360, 270, 25, 220)");
    }


    public void testComputeTrueCourseAndGroundSpeed() {
        NavigationServiceImpl service = new NavigationServiceImpl();

        String result1 = service.ComputeTrueCourseAndGroundsped(90, "45@20", 30.0, -80.0, 150);
        assertEquals("Data 8 163.0", result1);
        System.out.println("Test passed: computeTrueCourseAndGroundSpeed (90, 45@20, 30.0, -80.0, 150)");

        String result2 = service.ComputeTrueCourseAndGroundsped(180, "90@15", 45.0, -120.0, 200);
        assertEquals("Data 4 199.0", result2);
        System.out.println("Test passed: computeTrueCourseAndGroundSpeed (180, 90@15, 45.0, -120.0, 200)");

        String result3 = service.ComputeTrueCourseAndGroundsped(270, "180@10", -60.0, 100.0, 180);
        assertEquals("Data -3 190.0", result3);
        System.out.println("Test passed: computeTrueCourseAndGroundSpeed (270, 180@10, -60.0, 100.0, 180)");

        String result4 = service.ComputeTrueCourseAndGroundsped(360, "270@25", 0.0, 0.0, 220);
        assertEquals("Data 6 245.0", result4);
        System.out.println("Test passed: computeTrueCourseAndGroundSpeed (360, 270@25, 0.0, 0.0, 220)");
    }


	public static void main(String[] args) {
        NavigationApplicationTests tests = new NavigationApplicationTests();
        tests.testComputeCrosswindComponent();
        // tests.testComputeGroundSpeed();
        // tests.testComputeTrueCourseAndGroundSpeed();
    }
}

