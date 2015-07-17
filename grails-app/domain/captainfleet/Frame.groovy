package captainfleet

import com.vividsolutions.jts.geom.Geometry

class Frame extends BaseDomain {

    /**
     * Device emetteur du message
     */
    Device device

    // Station qui a recu le message
    /**
     *
     */
    Station station

    /**
     * Version de protocole utilisé par cette trame
     */
    FrameProtocol frameProtocol

    /**
     * Type de trame (trame de donnée, trame de service ou trame d'erreur)
     */
    FrameType frameType

    /**
     * Données traduites contenues dans les datas
     */
    FrameExtra frameExtra

    /**
     * Donnée utile contenue dans le message
     */
    String data

    /**
     * Date et heure de reception du message
     */
    Date time

    /**
     * Date et heure de reception du message (format epoch tel que recu du webservice)
     */
    Long epochTime

    /**
     * Indicateur de doublon
     * True si ce message est un doublon (message déjà recu par une autre station de base)
     */
    Boolean duplicate

    /**
     * Signal envoyé
     */
    Float snr

    /**
     * Signal moyen
     */
    Float avgSignal

    /**
     * Localisation approximative du signal envoyé
     * Localisation effectuée par triangularisation des stations de base SigFox
     */
    Point position

    /**
     * Received Signal Strength Indication, en dB
     */
    Float rssi

    /**
     * Position exacte (donnée envoyée par le GPS)
     */
    Geometry location

    static hasOne = [device: Device]

    static constraints = {
        device nullable: true
        time nullable: true
        frameProtocol nullable: true
        frameType nullable: true
        frameExtra nullable: true
        data nullable: true
        epochTime nullable: true
        duplicate nullable: true
        snr nullable: true
        station nullable: true
        avgSignal nullable: true
        position nullable: true
        rssi nullable: true
        location nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
