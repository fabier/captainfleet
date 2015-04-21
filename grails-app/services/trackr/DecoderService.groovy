package trackr

import grails.transaction.Transactional

@Transactional
class DecoderService {

    GeoConverterService geoConverterService

    /**
     * XXXXXXXXYYYYYYYYZABBCCDE (12 octets)
     * XXXXXXXX :  Latitude codée
     * YYYYYYYY: Longitude codée
     * ZA: (temps d'accroche GPS) a multiplier par 0,2 et par 3
     * BB: Tension panneau en résolution de 13mV
     * CC: Tension supercap en résolution de 13mV
     * D: 4 bits :nb de fois 16s ou la protection supercap a été active
     * "E: 1er bit: Flag Jour(1)/Nuit(0)
     * 3bits suivants: Compteur de trames"
     */
    DataDecoded tryDecode(String data) {
        DataDecoded dataDecoded = new DataDecoded(data)
        dataDecoded.tryDecode()
        if (dataDecoded.decoded) {
            return dataDecoded
        } else {
            return null
        }
    }
}
