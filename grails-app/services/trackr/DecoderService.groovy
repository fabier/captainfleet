package trackr

import grails.transaction.Transactional

@Transactional
class DecoderService {

    /**
     *
     * @param frame
     * @return
     */
    FrameData tryDecode(Frame frame) {
        if (frame) {
            switch (frame.frameProtocol) {
                case FrameProtocol.V1:
                    return tryDecode_V1(frame.data)
                case FrameProtocol.V2:
                    return tryDecode_V2(frame.data)
                default:
                    return tryDecode_V1(frame.data)
            }
        } else {
            null
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
                Double latitude = decodeLatitudeOrLongitude(data.substring(0, 8))

                // Longitude
                Double longitude = decodeLatitudeOrLongitude(data.substring(8, 16))

                // Temps d'accroche GPS (sec)
                Double gpsTimeToFix = Integer.parseInt(data.substring(16, 18), 16) * 0.6d

                // Tension Panneau Solaire (v)
                Double solarArrayVoltage = Integer.parseInt(data.substring(18, 20), 16) * 0.013d

                // Tension Supercap (v)
                Double superCapacitorVoltage = Integer.parseInt(data.substring(20, 22), 16) * 0.013d

                // Nb protect supercap (Nb de fois 16s)
                Integer superCapacitorProtectCount = Integer.parseInt(data.substring(22, 23), 16)

                // Bit de jour et compteur de trames
                Integer isDayAndFrameCount = Integer.parseInt(data.substring(23, 24), 16)
                Boolean isDay = (isDayAndFrameCount >> 3) == 1
                Integer frameCount = isDayAndFrameCount & 0b111

                if (latitude == 0d && longitude == 0d) {
                    latitude = null
                    longitude = null
                }

                frameData = new FrameData_V1(
                        data: data,
                        latitude: latitude,
                        longitude: longitude,
                        gpsTimeToFix: gpsTimeToFix,
                        solarArrayVoltage: solarArrayVoltage,
                        superCapacitorVoltage: superCapacitorVoltage,
                        superCapacitorProtectCount: superCapacitorProtectCount,
                        isDay: isDay,
                        frameCount: frameCount,
                        frameType: FrameType.MESSAGE
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
            int firstCharAsInt = Integer.parseInt(data.substring(0, 1), 16)
            if (firstCharAsInt in [0b0110, 0b0111, 0b1110, 0b1111]) {
                // Trame de service, on décode
                if (firstCharAsInt == 0b0110) {
                    // Informations de température
                    Integer frameCount = Integer.parseInt(data.substring(1, 2), 16) & 0b111

                    // Valeurs en °C entre -127°C et +127°C
                    Integer currentTemperature = integerToSmallInt(Integer.parseInt(data.substring(2, 4), 16))
                    Integer averageTemperature = integerToSmallInt(Integer.parseInt(data.substring(4, 6), 16))
                    Integer minTemperature = integerToSmallInt(Integer.parseInt(data.substring(6, 8), 16))
                    Integer maxTemperature = integerToSmallInt(Integer.parseInt(data.substring(8, 10), 16))

                    int superCapacitorProtectCountAndModemKOCount = Integer.parseInt(data.substring(10, 12), 16)
                    Integer superCapacitorProtectCount = (superCapacitorProtectCountAndModemKOCount >> 2) & 0b111111
                    Integer modemKOCount = superCapacitorProtectCountAndModemKOCount & 0b11

                    frameData = new FrameData_V2(
                            data: data,
                            frameCount: frameCount,
                            currentTemperature: currentTemperature,
                            averageTemperature: averageTemperature,
                            minTemperature: minTemperature,
                            maxTemperature: maxTemperature,
                            superCapacitorProtectCount: superCapacitorProtectCount,
                            modemKOCount: modemKOCount,
                            frameType: FrameType.SERVICE
                    )
                } else if (firstCharAsInt == 0b1110) {
                    // Trame d'erreur
                    // Plus d'infos : https://trello.com/c/WqL3vfkN

                    // AAAA : Type de trame d'erreur
                    Integer errorType = Integer.parseInt(data.substring(1, 2), 16)

                    // BBBBBBBB : Raison du message (voir https://trello.com/c/WqL3vfkN pour plus d'infos)
                    Integer reason = Integer.parseInt(data.substring(2, 4), 16)

                    Integer byte3 = Integer.parseInt(data.substring(4, 6), 16)
                    // CCCC : N/A
                    // D : Jour/Nuit
                    boolean isDay = ((byte3 >> 3) & 0b1) == 1
                    // EEE : Compteur

                    Integer byte4To5 = Integer.parseInt(data.substring(6, 10), 16)
                    // FFFFFF : N/A
                    // HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
                    Double solarArrayVoltage = ((byte4To5 >> 5) & 0b11111) * 0.1d

                    // IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
                    Double superCapacitorVoltage = (byte4To5 & 0b11111) * 0.09d

                    frameData = new FrameData_V2_Error(
                            data: data,
                            errorType: errorType,
                            reason: reason,
                            isDay: isDay,
                            solarArrayVoltage: solarArrayVoltage,
                            superCapacitorVoltage: superCapacitorVoltage,
                            frameType: FrameType.ERROR
                    )
                } else {
                    // On ne sait pas décoder, les données sont stockées en base, mais indéchiffrables
                }
            } else {
                if (data.length() == 24) {
                    // Déterminer si c'est une trame de service ou une trame d'infomation
                    // Les trames de services sont définies par une latitude > 90° ou < -90°
                    // Ce qui correspond en binaire à des valeurs pour les 4 premiers bits de
                    // 0110, 0111, 1110 ou 1111 (0x6, 0x7, 0xE ou 0xF)

                    // Trame normale, on décode
                    // Latitude
                    Double latitude = decodeLatitudeOrLongitude(data.substring(0, 8))

                    // Longitude
                    Double longitude = decodeLatitudeOrLongitude(data.substring(8, 16))

                    long last4Bytes = Long.parseLong(data.substring(16, 24), 16)

                    // AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
                    Integer hdop = (int) ((last4Bytes >> 30) & 0b11)

                    // BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
                    Integer satelliteCount = (int) ((last4Bytes >> 28) & 0b11)

                    // C : Flag Jour(1)/Nuit(0)
                    Boolean isDay = ((last4Bytes >> 27) & 0b1) == 1l

                    // DDD : Compteur de trames
                    Integer frameCount = (int) ((last4Bytes >> 24) & 0b111)

                    // EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
                    Double gpsTimeToFix = ((last4Bytes >> 20) & 0b1111) * 5.0d

                    // FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
                    Double speed = ((last4Bytes >> 15) & 0b11111) * 5.0d

                    // GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
                    Double azimuth = ((last4Bytes >> 10) & 0b11111) * 11.25d

                    // HHHHH : Tension panneau solaire entre 0 et 3.1V, pas de 100mV
                    Double solarArrayVoltage = ((last4Bytes >> 5) & 0b11111) * 0.1d

                    // IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
                    Double superCapacitorVoltage = (last4Bytes & 0b11111) * 0.09d

                    if (latitude == 0d && longitude == 0d) {
                        latitude = null
                        longitude = null
                    }

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
                            azimuth: azimuth,
                            frameType: FrameType.MESSAGE
                    )
                } else {
                    // On ne sait pas décoder
                }
            }
        } catch (Exception e) {
            // On n'a pas réussi à décoder...
            log.warn "Impossible to decode ${data}", e
        }
        return frameData
    }

    int integerToSmallInt(int i) {
        if (i >= 128) {
            128 - i
        } else {
            i
        }
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
