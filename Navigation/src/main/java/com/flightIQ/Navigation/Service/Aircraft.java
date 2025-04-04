package com.flightIQ.Navigation.Service;
import lombok.Getter;
import lombok.Setter;


public class Aircraft {

    @Getter @Setter private String name; 
    @Getter @Setter private double CLBFuelBurn;
    @Getter @Setter private double CRZfuelBurn;
    @Getter @Setter private double DescFuelbURN;


    public Aircraft(String aName, double CLB, double CRZ, double DES){

        this.name = aName;
        this.CLBFuelBurn = CLB;
        this.CRZfuelBurn = CRZ;
        this.DescFuelbURN = DES;

    }
}
