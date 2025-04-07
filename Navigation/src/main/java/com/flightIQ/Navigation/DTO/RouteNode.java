package com.flightIQ.Navigation.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RouteNode {
    String nodeName;
    double bearing;
    double distance;

    public RouteNode(String nodeName, double bearing, double distance) {
        this.nodeName = nodeName;
        this.bearing = bearing;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node: " + nodeName + ", Bearing: " + bearing + ", Distance: " + distance;
    }
}