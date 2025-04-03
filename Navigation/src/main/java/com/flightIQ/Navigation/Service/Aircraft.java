package com.flightIQ.Navigation.Service;
import lombok.Getter;
import lombok.Setter;


public class Aircraft {

    private String name; 
    private double CLBFuelBurn;
    private double CRZfuelBurn;
    private double DescFuelbURN;


    public Aircraft(String aName, double CLB, double CRZ, double DES){

        this.name = aName;
        this.CLBFuelBurn = CLB;
        this.CRZfuelBurn = CRZ;
        this.DescFuelbURN = DES;

    }
}
