package trackr

import grails.transaction.Transactional

@Transactional
class GeoConverterService {

    PointDecimal convert(PointDegreesMinutesSeconds pointDegreesMinutesSeconds) {
        PointDecimal pointDecimal = new PointDecimal()
        pointDecimal.latitude = pointDegreesMinutesSeconds.latitudeDegrees +
                pointDegreesMinutesSeconds.latitudeMinutes / 60.0f +
                pointDegreesMinutesSeconds.latitudeSeconds / 3600.0f
        pointDecimal.longitude = pointDegreesMinutesSeconds.longitudeDegrees +
                pointDegreesMinutesSeconds.longitudeMinutes / 60.0f +
                pointDegreesMinutesSeconds.longitudeSeconds / 3600.0f
        return pointDecimal
    }

    PointDegreesMinutesSeconds convert(PointDecimal pointDecimal) {
        PointDegreesMinutesSeconds pointDegreesMinutesSeconds = new PointDegreesMinutesSeconds()

        int latInt = (int) pointDecimal.latitude
        double latDecimalPart = pointDecimal.latitude - latInt
        double latMin = latDecimalPart * 60
        int latMinInt = (int) latInt
        int latMinDecimalPart = latMin - latMinInt
        pointDegreesMinutesSeconds.latitudeDegrees = latInt
        pointDegreesMinutesSeconds.latitudeMinutes = latMinInt
        pointDegreesMinutesSeconds.latitudeSeconds = latMinDecimalPart * 60

        int longInt = (int) pointDecimal.longitude
        double longDecimalPart = pointDecimal.longitude - longInt
        double longMin = longDecimalPart * 60
        int longMinInt = (int) longInt
        int longMinDecimalPart = longMin - longMinInt
        pointDegreesMinutesSeconds.longitudeDegrees = longInt
        pointDegreesMinutesSeconds.longitudeMinutes = longMinInt
        pointDegreesMinutesSeconds.longitudeSeconds = longMinDecimalPart * 60

        return pointDegreesMinutesSeconds
    }

    def toLatitudeStringDegrees(double latitude) {
        DegreeMinuteSecond degreeMinuteSecond = toDegreeMinuteSecond(latitude)
        return String.format(Locale.FRENCH, "%d°%02d'%02.3f'' %s",
                degreeMinuteSecond.degrees,
                degreeMinuteSecond.minutes,
                degreeMinuteSecond.seconds,
                degreeMinuteSecond.sign == 1 ? "N" : "S")
    }

    def toLongitudeStringDegrees(double longitude) {
        DegreeMinuteSecond degreeMinuteSecond = toDegreeMinuteSecond(longitude)
        return String.format(Locale.FRENCH, "%d°%02d'%02.3f'' %s",
                degreeMinuteSecond.degrees,
                degreeMinuteSecond.minutes,
                degreeMinuteSecond.seconds,
                degreeMinuteSecond.sign == 1 ? "E" : "W")
    }

    DegreeMinuteSecond toDegreeMinuteSecond(double latitudeLongitude) {
        DegreeMinuteSecond degreeMinuteSecond = new DegreeMinuteSecond()

        int sign = (int) Math.signum(latitudeLongitude)
        int intPart = (int) Math.abs(latitudeLongitude)
        double decimalPart = Math.abs(latitudeLongitude) - intPart
        double minute = decimalPart * 60
        int minuteIntPart = (int) minute
        double minuteDecimalPart = minute - minuteIntPart

        degreeMinuteSecond.sign = sign
        degreeMinuteSecond.degrees = intPart
        degreeMinuteSecond.minutes = minuteIntPart
        degreeMinuteSecond.seconds = minuteDecimalPart * 60

        return degreeMinuteSecond
    }

    class PointDecimal {
        double latitude
        double longitude
    }

    class PointDegreesMinutesSeconds {
        int latitudeDegrees
        int latitudeMinutes
        float latitudeSeconds
        int longitudeDegrees
        int longitudeMinutes
        float longitudeSeconds
    }

    class DegreeMinuteSecond {
        int sign
        int degrees
        int minutes
        float seconds
    }

}
