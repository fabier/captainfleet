package captainfleet

class FrameExtra extends BaseDomain {

    /**
     * Type de frame
     */
    FrameType frameType

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

    /**
     * Dilution horizontale
     */
    Integer hdop

    /**
     * Nombre de satellites
     */
    Integer satelliteCount

    /**
     * Vitesse en km/h
     */
    Double speed

    /**
     * Cap / Azimuth, en °, 0°=Nord, 90°=Est, 180°=Sud, 270°=Ouest
     */
    Double azimuth

    /**
     * Température actuelle (°C)
     */
    Integer currentTemperature

    /**
     * Température moyenne (°C)
     */
    Integer averageTemperature

    /**
     * Température minimale (°C)
     */
    Integer minTemperature

    /**
     * Température maximale (°C)
     */
    Integer maxTemperature

    /**
     * Nombre de fois où le modem est tombé en erreur (entre 0 et 3, 3 indique "3 fois ou plus")
     */
    Integer modemKOCount

    /**
     * Type d'erreur
     */
    Integer errorType

    /**
     * Raison du message d'erreur
     */
    Integer reason

    static belongsTo = [frame: Frame]

    static constraints = {
        frameType nullable: true
        latitude nullable: true
        longitude nullable: true
        gpsTimeToFix nullable: true
        solarArrayVoltage nullable: true
        superCapacitorVoltage nullable: true
        isDay nullable: true
        frameCount nullable: true
        superCapacitorProtectCount nullable: true
        hdop nullable: true
        satelliteCount nullable: true
        speed nullable: true
        azimuth nullable: true
        currentTemperature nullable: true
        averageTemperature nullable: true
        minTemperature nullable: true
        maxTemperature nullable: true
        modemKOCount nullable: true
        errorType nullable: true
        reason nullable: true
    }

    String hexaLatitude() {
        "0x${frame.data.substring(0, 8).toUpperCase()}"
    }

    String hexaLongitude() {
        "0x${frame.data.substring(8, 16).toUpperCase()}"
    }

    String hexaGpsTimeToFix() {
        // EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
        String.format("0b%4s", Integer.toBinaryString((int) (gpsTimeToFix / 5.0d))).replaceAll(' ', '0')
    }

    String hexaSolarArrayVoltage() {
        // HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
        String.format("0b%5s", Integer.toBinaryString((int) (solarArrayVoltage / 0.1d))).replaceAll(' ', '0')
    }

    String hexaSuperCapacitorVoltage() {
        // IIIII : Tension supercapacité entre 0 et 2.79V, pas de 90mV
        String.format("0b%5s", Integer.toBinaryString((int) (superCapacitorVoltage / 0.09d))).replaceAll(' ', '0')
    }

    String hexaAzimuth() {
        // GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
        String.format("0b%5s", Integer.toBinaryString((int) (azimuth / 11.25d))).replaceAll(' ', '0')
    }

    String hexaHdop() {
        // AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
        String.format("0b%2s", Integer.toBinaryString(hdop)).replaceAll(' ', '0')
    }

    String hexaSatelliteCount() {
        // BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
        String.format("0b%2s", Integer.toBinaryString(satelliteCount)).replaceAll(' ', '0')
    }

    String hexaSpeed() {
        // FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
        String.format("0b%5s", Integer.toBinaryString((int) (speed / 5d))).replaceAll(' ', '0')
    }

    String hexaCurrentTemperature() {
        "0x${frame.data.substring(2, 4).toUpperCase()}"
    }

    String hexaAverageTemperature() {
        "0x${frame.data.substring(4, 6).toUpperCase()}"
    }

    String hexaMinTemperature() {
        "0x${frame.data.substring(6, 8).toUpperCase()}"
    }

    String hexaMaxTemperature() {
        "0x${frame.data.substring(8, 10).toUpperCase()}"
    }

    String hexaSuperCapacitorProtectCount() {
        String.format("0b%6s", Integer.toBinaryString(superCapacitorProtectCount)).replaceAll(' ', '0')
    }

    String hexaModemKOCount() {
        String.format("0b%2s", Integer.toBinaryString(modemKOCount)).replaceAll(' ', '0')
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
