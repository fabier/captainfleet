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
     * Longitude en ° [-180.0180.0]
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

    def hexaLatitude() {
        data.substring(0, 8).toUpperCase()
    }

    def hexaLongitude() {
        data.substring(8, 16).toUpperCase()
    }

    def hexaGpsTimeToFix() {
        data.substring(16, 18).toUpperCase()
    }

    def hexaSolarArrayVoltage() {
        data.substring(18, 20).toUpperCase()
    }

    def hexaSuperCapacitorVoltage() {
        data.substring(20, 22).toUpperCase()
    }

    def hexaSuperCapacitorProtectCount() {
        data.substring(22, 23).toUpperCase()
    }
}
