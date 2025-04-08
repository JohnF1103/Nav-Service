package com.flightIQ.Navigation.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteNode {
    private String nodeName;
    private double bearing;
    private double distance;

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