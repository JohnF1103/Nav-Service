package com.flightIQ.Navigation.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class WindAloft {
    private int direction;
    private int speed;
    private String closestAirportCode;
    private double distanceFromOriginalAirportInMiles;

    public static WindAloft fromString(String data) {
        String[] parts = data.split("@");
        System.out.println("LEN " + parts.length + " " + data);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid WindAloft data format");
        }
        WindAloft windAloft = new WindAloft();
        windAloft.setDirection(Integer.parseInt(parts[0]));
        windAloft.setSpeed(Integer.parseInt(parts[1]));
        windAloft.setClosestAirportCode(parts[3]);
        windAloft.setDistanceFromOriginalAirportInMiles(Double.parseDouble(parts[4]));
        return windAloft;
    }

    @Override
    public String toString() {
        return String.format("%d@%d@%s@%.2f", direction, speed, closestAirportCode, distanceFromOriginalAirportInMiles);
    }
}