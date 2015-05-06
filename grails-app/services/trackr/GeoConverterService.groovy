package trackr

import grails.transaction.Transactional

@Transactional
class GeoConverterService {

    PointDecimal convert(PointDegreesMinutesSeconds pointDegreesMinutesSeconds) {
        PointDecimal pointDecimal = new PointDecimal()
        pointDecimal.latitude = pointDegreesMinutesSeconds.latitudeDegrees
        +pointDegreesMinutesSeconds.latitudeMinutes / 60.0d
        +pointDegreesMinutesSeconds.latitudeSeconds / 3600.0d
        pointDecimal.longitude = pointDegreesMinutesSeconds.longitudeDegrees
        +pointDegreesMinutesSeconds.longitudeMinutes / 60.0d
        +pointDegreesMinutesSeconds.longitudeSeconds / 3600.0d
        return pointDecimal
    }

    PointDegreesMinutesSeconds convert(PointDecimal pointDecimal) {
        PointDegreesMinutesSeconds pointDegreesMinutesSeconds = new PointDegreesMinutesSeconds()

        int latInt = (int) pointDecimal.latitude
        double latDecimalPart = pointDecimal.latitude - latInt
        double latMin = latDecimalPart * 60
        int latMinInt = (int) latMin
        double latMinDecimalPart = (double) (latMin - latMinInt)
        pointDegreesMinutesSeconds.latitudeDegrees = latInt
        pointDegreesMinutesSeconds.latitudeMinutes = latMinInt
        pointDegreesMinutesSeconds.latitudeSeconds = latMinDecimalPart * 60

        int longInt = (int) pointDecimal.longitude
        double longDecimalPart = pointDecimal.longitude - longInt
        double longMin = longDecimalPart * 60
        int longMinInt = (int) longMin
        double longMinDecimalPart = longMin - longMinInt
        pointDegreesMinutesSeconds.longitudeDegrees = longInt
        pointDegreesMinutesSeconds.longitudeMinutes = longMinInt
        pointDegreesMinutesSeconds.longitudeSeconds = longMinDecimalPart * 60

        return pointDegreesMinutesSeconds
    }

    double convert(DegreeMinuteSecond degreeMinuteSecond) {
        return convert(degreeMinuteSecond.signum, degreeMinuteSecond.degrees, degreeMinuteSecond.minutes, degreeMinuteSecond.seconds)
    }

    double convert(int signum, int degrees, int minutes, double seconds) {
        double value = Math.abs(degrees) + minutes / 60.0d + seconds / 3600.0d
        if (signum < 0) {
            value = -value
        }
        return value
    }

    DegreeMinuteSecond convert(double latitudeLongitude) {
        DegreeMinuteSecond degreeMinuteSecond = new DegreeMinuteSecond()

        double latitudeLongitudeCopy = latitudeLongitude
        latitudeLongitudeCopy = Math.abs(latitudeLongitudeCopy)
        int intPart = (int) Math.floor(latitudeLongitudeCopy)
        double decimalPart = latitudeLongitudeCopy - intPart
        double minute = decimalPart * 60.0d
        int minuteIntPart = (int) Math.floor(minute)
        double minuteDecimalPart = minute - minuteIntPart

        degreeMinuteSecond.signum = (int) Math.signum(latitudeLongitude)
        degreeMinuteSecond.degrees = intPart
        degreeMinuteSecond.minutes = minuteIntPart
        degreeMinuteSecond.seconds = minuteDecimalPart * 60.0d

        return degreeMinuteSecond
    }

    String toLatitudeStringDegrees(double latitude) {
        DegreeMinuteSecond degreeMinuteSecond = convert(latitude)
        return degreeMinuteSecond.toStringAbs() + " " + (degreeMinuteSecond.signum > 0 ? "N" : "S")
    }

    String toLongitudeStringDegrees(double longitude) {
        DegreeMinuteSecond degreeMinuteSecond = convert(longitude)
        return degreeMinuteSecond.toStringAbs() + " " + (degreeMinuteSecond.signum > 0 ? "E" : "W")
    }

    class PointDecimal {

        double latitude
        double longitude

        PointDecimal() {
        }

        PointDecimal(double latitude, double longitude) {
            this.latitude = latitude
            this.longitude = longitude
        }
    }

    class PointDegreesMinutesSeconds {

        int latitudeDegrees
        int latitudeMinutes
        double latitudeSeconds
        int longitudeDegrees
        int longitudeMinutes
        double longitudeSeconds

        PointDegreesMinutesSeconds() {
        }

        PointDegreesMinutesSeconds(int latitudeDegrees, int latitudeMinutes, double latitudeSeconds, int longitudeDegrees, int longitudeMinutes, double longitudeSeconds) {
            this.latitudeDegrees = latitudeDegrees
            this.latitudeMinutes = latitudeMinutes
            this.latitudeSeconds = latitudeSeconds
            this.longitudeDegrees = longitudeDegrees
            this.longitudeMinutes = longitudeMinutes
            this.longitudeSeconds = longitudeSeconds
        }
    }

    class DegreeMinuteSecond {

        int signum
        int degrees
        int minutes
        double seconds

        DegreeMinuteSecond() {
        }

        DegreeMinuteSecond(int signum, int degrees, int minutes, double seconds) {
            this.signum = signum
            this.degrees = degrees
            this.minutes = minutes
            this.seconds = seconds
        }

        @Override
        String toString() {
            return String.format(Locale.FRENCH, "%s%d°%02d'%02.3f''", signum > 0 ? "" : "-1", degrees, minutes, seconds)
        }

        String toStringAbs() {
            return String.format(Locale.FRENCH, "%d°%02d'%02.3f''", degrees, minutes, seconds)
        }
    }
}
