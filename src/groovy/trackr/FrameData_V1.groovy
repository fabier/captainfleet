package trackr

import groovy.util.logging.Log

import java.util.logging.Level

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
@Log
class FrameData_V1 extends FrameData {

    float gpsTimeToFix
    float solarArrayVoltage
    float superCapacitorVoltage
    int superCapacitorProtectCount
    int isDayAndFrameCount
    boolean isDay
    int frameCount

    FrameData_V1(String data) {
        super(data)
    }

    String hexaGpsTimeToFix() {
        return data.substring(16, 18)
    }

    String hexaSolarArrayVoltage() {
        return data.substring(18, 20)
    }

    String hexaSuperCapacitorVoltage() {
        return data.substring(20, 22)
    }

    String hexaSuperCapacitorProtectCount() {
        return data.substring(22, 23)
    }

    String hexaDayBitAndFrameCount() {
        return data.substring(23, 24)
    }

    int intDayBitAndFrameCount() {
        return Integer.parseInt(hexaDayBitAndFrameCount(), 16)
    }

    void tryDecode() {
        try {
            if (!decoded && data.length() == 24) {
                // Latitude
                latitude = decodeLatitude() // Longitude
                longitude = decodeLongitude() // Temps d'accroche GPS (sec)
                gpsTimeToFix = (float) (Integer.parseInt(hexaGpsTimeToFix(), 16) * 0.2 * 3)
                // Tension Panneau Solaire (v)
                solarArrayVoltage = (float) (Integer.parseInt(hexaSolarArrayVoltage(), 16) * 0.013)
                // Tension Supercap (v)
                superCapacitorVoltage = (float) (Integer.parseInt(hexaSuperCapacitorVoltage(), 16) * 0.013)
                // Nb protect supercap (Nb de fois 16s)
                superCapacitorProtectCount = Integer.parseInt(hexaSuperCapacitorProtectCount(), 16)
                // Bit de jour et compteur de trames
                isDayAndFrameCount = intDayBitAndFrameCount()
                isDay = (isDayAndFrameCount >> 3) == 1
                frameCount = isDayAndFrameCount & 0b111

                decoded = true
            }
        } catch (Exception e) {
            // On n'a pas réussi à décoder...
            log.log Level.WARNING, "Impossible to decode ${data}", e
        }
    }
}
