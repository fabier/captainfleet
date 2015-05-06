package trackr

import groovy.util.logging.Log

import java.util.logging.Level


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
}