package trackr

/**
 * Trame d'erreur pour la version 2 des trackers
 *
 * 1110AAAA
 * BBBBBBBB
 * CCCCDEEE
 * FFFFFFHH
 * HHHIIIII
 *
 * AAAA : Type de trame d'erreur
 * BBBBBBBB : Raison du message (voir https://trello.com/c/WqL3vfkN pour plus d'infos)
 * CCCC : N/A
 * D : Jour/Nuit
 * EEE : Compteur
 * FFFFFF : N/A
 * HHHHH : Tension panneau solaire entre 0 et 3.1V pas de 100mV
 * IIIII : Tension supercapacité entre 0 et 2.79V pas de 90mV
 */
class FrameData_V2_Error extends FrameData_V2 {

    /**
     * Type d'erreur
     */
    Integer errorType

    /**
     * Raison du message d'erreur
     */
    Integer reason
}
