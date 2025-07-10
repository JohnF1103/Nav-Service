package com.flightIQ.Navigation.DTO;

import java.util.List;

public record StateVector(
        String icao24,
        String callSign,

        /** Time since last Position update. */
        Integer timePosition,

        /** Time since last general update. */
        Integer lastContact,

        Float longitude,
        Float latitude,

        Float baroAltitude,
        Float geoAltitude,
        boolean onGround,

        Float velocity,
        Float verticalVelocity,

        /** Heading in degrees. North = 0 degrees. CW Positive[?] */
        Float trueTrack,

        String squak,
        int poseSource

) {
    public static StateVector fromList(List<Object> data) {
        if (data.get(14) != null) System.out.println(data);
        return new StateVector(
                (String) data.get(0),
                (String) data.get(1),
                toInteger(data.get(3)),
                toInteger(data.get(4)),
                toFloat(data.get(5)),
                toFloat(data.get(6)),
                toFloat(data.get(7)),
                toFloat(data.get(13)),
                toBoolean(data.get(8)),
                toFloat(data.get(9)),
                toFloat(data.get(11)),
                toFloat(data.get(10)),
                (String) data.get(14),
                toInteger(data.get(16))
        );
    }

    private static Float toFloat(Object o) {
        return o instanceof Number n ? n.floatValue() : null;
    }

    private static Integer toInteger(Object o) {
        return o instanceof Number n ? n.intValue() : null;
    }

    private static Boolean toBoolean(Object o) {
        return o instanceof Boolean b ? b : null;
    }
}