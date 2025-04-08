package com.flightIQ.Navigation.DTO;
import lombok.Getter;
import lombok.Setter;


public class Aircraft {

    @Getter @Setter private String name; 
    @Getter @Setter private double CLBFuelBurn;  
    @Getter @Setter private double CRZfuelBurn;
    @Getter @Setter private double DescFuelbURN;

    @Getter @Setter private int takeoffDistance;
    @Getter @Setter private int landingDistance;
    @Getter @Setter private int basicEmptyWeight;

    public Aircraft(String aName, double CLB, double CRZ, double DES, int takeoffDist, int landingDist, int emptyWeight) {
        this.name = aName;
        this.CLBFuelBurn = CLB;
        this.CRZfuelBurn = CRZ;
        this.DescFuelbURN = DES;
        this.takeoffDistance = takeoffDist;
        this.landingDistance = landingDist;
        this.basicEmptyWeight = emptyWeight;
    }
}
