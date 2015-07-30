package captainfleet

/**
 * Association entre device et zone pour regrouper plus facilement ces information dans les logs de positionnement d'un device
 *
 * Contient le dernier état connu d'un boitier en regard d'une zone (dans la zone ou hors de la zone).
 */
class DeviceZone extends BaseDomain {

    /**
     * Boitier
     */
    Device device

    /**
     * Zone
     */
    Zone zone

    /**
     * Indique si le boitier est actuellement dans cette zone
     */
    Boolean isRaised

    static constraints = {
        device nullable: false, unique: ['zone']
        isRaised nullable: true
    }

    static void removeAll(Zone a) {
        DeviceZone.where { zone == a }.deleteAll()
    }

    static void removeAll(Device d) {
        DeviceZone.where { device == d }.deleteAll()
    }
}
