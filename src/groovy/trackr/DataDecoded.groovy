package trackr

import groovy.util.logging.Log4j

@Log4j
class DataDecoded {

    String data
    boolean decoded

    double latitude
    double longitude
    float gpsTimeToFix
    float solarArrayVoltage
    float superCapacitorVoltage
    int superCapacitorProtectCount
    int isDayAndFrameCount
    boolean isDay
    int frameCount

    DataDecoded(String data) {
        this.data = data
    }

    String hexaLatitudeFirstDigit() {
        return data.substring(0, 1)
    }

    String hexaLatitude() {
        return data.substring(0, 8)
    }

    String hexaLongitudeFirstDigit() {
        return data.substring(8, 9)
    }

    String hexaLongitude() {
        return data.substring(8, 16)
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
                int latitudeSign = Integer.parseInt(hexaLatitudeFirstDigit(), 16) >> 3 == 1 ? -1 : 1
                int encodedLatitude = ((int) Long.parseLong(hexaLatitude(), 16) & ((1 << 31) - 1))
                float xSeconds = (encodedLatitude % 10000)
                xSeconds = 60.0 * xSeconds / 10000
                int xMinutes = ((int) (encodedLatitude / 1E4)) % 100
                int xDegrees = ((int) (encodedLatitude / 1E6))
                latitude = latitudeSign * (xDegrees + xMinutes / 60 + xSeconds / 3600)

                // Longitude
                int longitudeSign = Integer.parseInt(hexaLongitudeFirstDigit(), 16) >> 3 == 1 ? -1 : 1
                int encodedLongitude = ((int) Long.parseLong(hexaLongitude(), 16) & ((1 << 31) - 1))
                float ySeconds = (encodedLongitude % 10000)
                ySeconds = 60.0 * ySeconds / 10000
                int yMinutes = ((int) (encodedLongitude / 1E4)) % 100
                int yDegrees = ((int) (encodedLongitude / 1E6))
                longitude = longitudeSign * (yDegrees + yMinutes / 60 + ySeconds / 3600)

                // Temps d'accroche GPS (sec)
                gpsTimeToFix = Integer.parseInt(hexaGpsTimeToFix(), 16) * 0.2 * 3

                // Tension Panneau Solaire (v)
                solarArrayVoltage = Integer.parseInt(hexaSolarArrayVoltage(), 16) * 0.013

                // Tension Supercap (v)
                superCapacitorVoltage = Integer.parseInt(hexaSuperCapacitorVoltage(), 16) * 0.013

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
            log.warn("Impossible to decode ${data}", e)
        }
    }
}
