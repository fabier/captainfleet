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
    Double latitude

    /**
     * Longitude en ° [-180.0:180.0]
     */
    Double longitude

    /**
     * Durée d'acquisition GPS en secondes
     */
    Double gpsTimeToFix

    /**
     * Tension panneau solaire en V
     */
    Double solarArrayVoltage

    /**
     * Tension supercapacité en V
     */
    Double superCapacitorVoltage

    /**
     * Flag indiquant si la trame a été envoyée de jour ou de nuit
     */
    Boolean isDay

    /**
     * Compteur de trames
     */
    Integer frameCount

    /**
     * Nombre de fois où la super capacité a été déchargée pour la protéger
     */
    Integer superCapacitorProtectCount

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

    String hexaCurrentTemperature() {
        null
    }

    String hexaAverageTemperature() {
        null
    }

    String hexaMinTemperature() {
        null
    }

    String hexaMaxTemperature() {
        null
    }

    String hexaModemKOCount() {
        null
    }

    final String hexaIsDay() {
        String.format("0b%1s", Integer.toBinaryString(isDay ? 1 : 0)).replaceAll(' ', '0')
    }

    final String hexaFrameCount() {
        String.format("0b%3s", Integer.toBinaryString(frameCount)).replaceAll(' ', '0')
    }

    boolean hasGeolocationData() {
        latitude != null && longitude != null
    }
}
