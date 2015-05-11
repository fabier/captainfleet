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
class FrameData_V1 extends FrameData {

    /**
     * Nombre de fois où la super capacité a été déchargée pour la protéger
     */
    int superCapacitorProtectCount
}
