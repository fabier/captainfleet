package captainfleet

/**
 * Cette table reprend les données de la table de logs DeviceZoneLog,
 * mais n'a qu'une seule ligne pour indiquer un changement d'état
 *
 * Celà permet notamment de compter le nombre de fois où un boitier est entré ou sorti d'une zone pendant une période donnée.
 * Celà permet aussi de compter le nombre de boitiers qui sont entrés ou sortis dans telle ou telle zone sur une période données.
 */
class DeviceZoneLogAggregate extends BaseDomain {

    /**
     * Boitier concerné
     */
    Device device

    /**
     * Zone concernée
     */
    Zone zone

    /**
     * Trame qui est à l'origine de ce log
     */
    Frame frame

    /**
     * Indique si le boitier est dans la zone pour ce log
     */
    Boolean isRaised

    static hasOne = [device: Device, zone: Zone]

    static constraints = {
        isRaised nullable: true
        frame nullable: true
    }
}
