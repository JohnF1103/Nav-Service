package com.flightIQ.Navigation;

import com.flightIQ.Navigation.Service.NavigationServiceImpl;


public class NavigationServiceTest {

    public static void main(String[] args) {
        NavigationServiceImpl service = new NavigationServiceImpl();

        // Test case 1
        System.out.println("Test Case 1:");
        System.out.println(service.ComputeTrueCourseAndGroundsped(300, "230@20", 26.2473600, -80.1111272, 150));

        // Test case 2
        System.out.println("Test Case 2:");
        System.out.println(service.ComputeTrueCourseAndGroundsped(45, "90@15", 34.052235, -118.243683, 120));

        // Test case 3
        System.out.println("Test Case 3:");
        System.out.println(service.ComputeTrueCourseAndGroundsped(270, "360@10", 51.507351, -0.127758, 200));

        // Test case 4
        System.out.println("Test Case 4:");
        System.out.println(service.ComputeTrueCourseAndGroundsped(180, "270@25", -33.868820, 151.209290, 180));

        // Test case 5
        System.out.println("Test Case 5:");
        System.out.println(service.ComputeTrueCourseAndGroundsped(135, "45@30", 35.689487, 139.691711, 160));
    }
}