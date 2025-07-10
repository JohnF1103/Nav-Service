package com.flightIQ.Navigation.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aircraft {

    private String name;
    private double CLBFuelBurn;
    private double CRZfuelBurn;
    private double DescFuelbURN;
    private int takeoffDistance;
    private int landingDistance;
    private int basicEmptyWeight;

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
