package trackr

/**
 * Created by fabier on 30/04/15.
 */
abstract class FrameData {

    /**
     * Donnée brute provenant du device
     */
    String data

    /**
     * Latitude en ° [-90.0:90.0]
     */
    double latitude

    /**
     * Longitude en ° [-180.0:180.0]
     */
    double longitude

    /**
     * Durée d'acquisition GPS en secondes
     */
    double gpsTimeToFix

    /**
     * Tension panneau solaire en V
     */
    double solarArrayVoltage

    /**
     * Tension supercapacité en V
     */
    double superCapacitorVoltage

    /**
     * Flag indiquant si la trame a été envoyée de jour ou de nuit
     */
    boolean isDay

    /**
     * Compteur de trames
     */
    int frameCount

    String hexaLatitude() {
        "0x${data.substring(0, 8).toUpperCase()}"
    }

    String hexaLongitude() {
        "0x${data.substring(8, 16).toUpperCase()}"
    }

    String hexaGpsTimeToFix() {
        null
    }

    String hexaSolarArrayVoltage() {
        null
    }

    String hexaSuperCapacitorVoltage() {
        null
    }

    String hexaSuperCapacitorProtectCount() {
        null
    }

    String hexaHdop() {
        null
    }

    String hexaSatelliteCount() {
        null
    }

    String hexaSpeed() {
        null
    }

    String hexaAzimuth() {
        null
    }

    final String hexaIsDay() {
        String.format("0b%1s", Integer.toBinaryString(isDay ? 1 : 0)).replaceAll(' ', '0')
    }

    final String hexaFrameCount() {
        String.format("0b%3s", Integer.toBinaryString(frameCount)).replaceAll(' ', '0')
    }

    boolean hasGeolocationData() {
        Math.abs(longitude) >= Float.MIN_VALUE && Math.abs(latitude) >= Float.MIN_VALUE
    }
}
