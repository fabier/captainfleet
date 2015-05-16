package trackr
/**
 * XXXXXXXX
 * XXXXXXXX
 * XXXXXXXX
 * XXXXXXXX: Latitude codée (4 octets)
 * YYYYYYYY
 * YYYYYYYY
 * YYYYYYYY
 * YYYYYYYY: Longitude codée (4 octets)
 * AABBCDDD : autres infos (1 octet)
 * EEEEFFFF : autres infos (1 octet)
 * FGGGGGHH : autres infos (1 octet)
 * HHHIIIII : autres infos (1 octet)
 * Total : 12 octets
 *
 * AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
 * BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
 * C : Flag Jour(1)/Nuit(0)
 * DDD : Compteur de trames
 * EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
 * FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
 * GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
 * HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
 * IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
 */
class FrameData_V2 extends FrameData {

    /**
     * Dilution horizontale
     */
    int hdop

    /**
     * Nombre de satellites
     */
    int satelliteCount

    /**
     * Vitesse en km/h
     */
    float speed

    /**
     * Cap / Azimuth, en °, 0°=Nord, 90°=Est, 180°=Sud, 270°=Ouest
     */
    float azimuth

    @Override
    String hexaGpsTimeToFix() {
        // EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
        String.format("0b%4s", Integer.toBinaryString((int) (gpsTimeToFix / 5.0d))).replaceAll(' ', '0')
    }

    @Override
    String hexaSolarArrayVoltage() {
        // IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
        String.format("0b%5s", Integer.toBinaryString((int) (superCapacitorVoltage / 0.09d))).replaceAll(' ', '0')
    }

    @Override
    String hexaSuperCapacitorVoltage() {
        // HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
        String.format("0b%5s", Integer.toBinaryString((int) (solarArrayVoltage / 0.1d))).replaceAll(' ', '0')
    }

    @Override
    String hexaAzimuth() {
        // GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
        String.format("0b%5s", Integer.toBinaryString((int) (azimuth / 11.25d))).replaceAll(' ', '0')
    }

    @Override
    String hexaHdop() {
        // AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
        String.format("0b%2s", Integer.toBinaryString(hdop)).replaceAll(' ', '0')
    }

    @Override
    String hexaSatelliteCount() {
        // BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
        String.format("0b%2s", Integer.toBinaryString(satelliteCount)).replaceAll(' ', '0')
    }

    @Override
    String hexaSpeed() {
        // FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
        String.format("0b%5s", Integer.toBinaryString((int) (speed / 5d))).replaceAll(' ', '0')
    }
}