package trackr
/**
 * <code>
 * XXXXXXXXYYYYYYYYZABBCCDE (12 octets)<br/>
 * XXXXXXXX : Latitude codée<br/>
 * YYYYYYYY : Longitude codée<br/>
 * ZA: (temps d'accroche GPS) a multiplier par 0,2 et par 3<br/>
 * BB: Tension panneau en résolution de 13mV<br/>
 * CC: Tension supercap en résolution de 13mV<br/>
 * D: 4 bits :nb de fois 16s ou la protection supercap a été active<br/>
 * "E: 1er bit: Flag Jour(1)/Nuit(0)<br/>
 * 3bits suivants: Compteur de trames"<br/>
 * </code>
 */
class FrameData_V1 extends FrameData {

    @Override
    String hexaGpsTimeToFix() {
        "0x${data.substring(16, 18).toUpperCase()}"
    }

    @Override
    String hexaSolarArrayVoltage() {
        "0x${data.substring(18, 20).toUpperCase()}"
    }

    @Override
    String hexaSuperCapacitorVoltage() {
        "0x${data.substring(20, 22).toUpperCase()}"
    }

    @Override
    String hexaSuperCapacitorProtectCount() {
        "0x${data.substring(22, 23).toUpperCase()}"
    }
}
