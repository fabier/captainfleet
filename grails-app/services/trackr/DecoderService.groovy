package trackr

import grails.transaction.Transactional

@Transactional
class DecoderService {
    FrameData_V1 tryDecode(Frame frame) {
        switch (frame.frameProtocol) {
            case FrameProtocol.V1:
                return tryDecode_V1(frame.data)
            case FrameProtocol.V2:
                return tryDecode_V2(frame.data)
            default:
                return tryDecode_V1(frame.data)
        }
    }

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
     *
     * @param data
     * @return
     */
    FrameData_V1 tryDecode_V1(String data) {
        FrameData_V1 frameData = null
        try {
            if (data.length() == 24) {
                // Latitude
                double latitude = decodeLatitudeOrLongitude(data.substring(0, 8))

                // Longitude
                double longitude = decodeLatitudeOrLongitude(data.substring(8, 16))

                // Temps d'accroche GPS (sec)
                double gpsTimeToFix = Integer.parseInt(data.substring(16, 18), 16) * 0.6d

                // Tension Panneau Solaire (v)
                double solarArrayVoltage = Integer.parseInt(data.substring(18, 20), 16) * 0.013d

                // Tension Supercap (v)
                double superCapacitorVoltage = Integer.parseInt(data.substring(20, 22), 16) * 0.013d

                // Nb protect supercap (Nb de fois 16s)
                int superCapacitorProtectCount = Integer.parseInt(data.substring(22, 23), 16)

                // Bit de jour et compteur de trames
                int isDayAndFrameCount = Integer.parseInt(data.substring(23, 24), 16)
                boolean isDay = (isDayAndFrameCount >> 3) == 1
                int frameCount = isDayAndFrameCount & 0b111

                frameData = new FrameData_V1(
                        data: data,
                        latitude: latitude,
                        longitude: longitude,
                        gpsTimeToFix: gpsTimeToFix,
                        solarArrayVoltage: solarArrayVoltage,
                        superCapacitorVoltage: superCapacitorVoltage,
                        superCapacitorProtectCount: superCapacitorProtectCount,
                        isDay: isDay,
                        frameCount: frameCount
                )
            }
        } catch (Exception e) {
            // On n'a pas réussi à décoder...
            log.warn "Impossible to decode ${data}", e
        }
        return frameData
    }

    /**
     * <code>
     * XXXXXXXX<br/>
     * XXXXXXXX<br/>
     * XXXXXXXX<br/>
     * XXXXXXXX: Latitude codée (4 octets)<br/>
     * YYYYYYYY<br/>
     * YYYYYYYY<br/>
     * YYYYYYYY<br/>
     * YYYYYYYY: Longitude codée (4 octets)<br/>
     * AABBCDDD : autres infos (1 octet)<br/>
     * EEEEFFFF : autres infos (1 octet)<br/>
     * FGGGGGHH : autres infos (1 octet)<br/>
     * HHHIIIII : autres infos (1 octet)<br/>
     * Total : 12 octets<br/>
     * <br/>
     * AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)<br/>
     * BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)<br/>
     * C : Flag Jour(1)/Nuit(0)<br/>
     * DDD : Compteur de trames<br/>
     * EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s<br/>
     * FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h<br/>
     * GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°<br/>
     * HHHHH : Tension panneau entre 0 et 3.1V, pas de 100mV<br/>
     * IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV<br/>
     * </code>
     *
     * @param data Chaine de caractères au format Hexa correspondant à la donnée data envoyée par le device
     * @return frameData , un objet contenant toutes les données décodées.
     */
    FrameData_V2 tryDecode_V2(String data) {
        FrameData_V2 frameData = null
        try {
            if (data.length() == 24) {
                // Latitude
                def latitude = decodeLatitudeOrLongitude(data.substring(0, 8))

                // Longitude
                def longitude = decodeLatitudeOrLongitude(data.substring(8, 16))

                long last4Bytes = Long.parseLong(data.substring(16, 24), 16)

                // AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
                def hdop = (int) ((last4Bytes >> 30) & 0b11)

                // BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
                def satelliteCount = (int) ((last4Bytes >> 28) & 0b11)

                // C : Flag Jour(1)/Nuit(0)
                def isDay = ((last4Bytes >> 27) & 0b1) == 1l

                // DDD : Compteur de trames
                def frameCount = (int) ((last4Bytes >> 24) & 0b111)

                // EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
                def gpsTimeToFix = ((last4Bytes >> 20) & 0b1111) * 5.0d

                // FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
                def speed = ((last4Bytes >> 15) & 0b11111) * 5d

                // GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
                def azimuth = ((last4Bytes >> 10) & 0b11111) * 11.25d

                // HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
                def solarArrayVoltage = ((last4Bytes >> 5) & 0b11111) * 0.1d

                // IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
                def superCapacitorVoltage = (last4Bytes & 0b11111) * 0.9d

                frameData = new FrameData_V2(
                        data: data,
                        latitude: latitude,
                        longitude: longitude,
                        gpsTimeToFix: gpsTimeToFix,
                        solarArrayVoltage: solarArrayVoltage,
                        superCapacitorVoltage: superCapacitorVoltage,
                        isDay: isDay,
                        frameCount: frameCount,
                        hdop: hdop,
                        satelliteCount: satelliteCount,
                        speed: speed,
                        azimuth: azimuth
                )
            }
        } catch (Exception e) {
            // On n'a pas réussi à décoder...
            log.warn "Impossible to decode ${data}", e
        }
        return frameData
    }

    static double decodeLatitudeOrLongitude(String latitudeEncoded) {
        int latitudeSign = Integer.parseInt(latitudeEncoded.substring(0, 1), 16) >> 3 == 1 ? -1 : 1
        int filter = (1 << 31) - 1
        long encodedLatitude = Long.parseLong(latitudeEncoded, 16) & filter
        double xSeconds = (encodedLatitude % 10000)
        xSeconds = (60.0d * xSeconds / 10000.0d)
        long xMinutes = (encodedLatitude / 10000.0d) % 100
        long xDegrees = encodedLatitude / 1000000.0d
        return latitudeSign * (xDegrees + xMinutes / 60.0d + xSeconds / 3600.0d)
    }
}
