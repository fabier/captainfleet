package trackr

class Frame extends BaseDomain {

    // Device emetteur du message
    Device device

    // Station qui a recu le message
    Station station

    // Donnée utile
    String data

    // Date et heure de reception du message
    Date time

    // Date et heure de reception du message (format epoch tel que recu du webservice)
    Long epochTime

    // true si ce message est un doublon
    Boolean duplicate

    // Signal envoyé
    Float signal

    // Signal moyen
    Float avgSignal

    // Localisation approximative du signal envoyé
    Point position

    // Received Signal Strength Indication, en dB
    Float rssi

    static hasOne = [device: Device]

    static constraints = {
        device nullable: true
        time nullable: true
        epochTime nullable: true
        duplicate nullable: true
        signal nullable: true
        station nullable: true
        data nullable: true
        avgSignal nullable: true
        position nullable: true
        rssi nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
