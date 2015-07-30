package captainfleet

class DeviceZoneLog extends BaseDomain {

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

    static constraints = {
        isRaised nullable: true
        device nullable: true
        zone nullable: true
        frame nullable: true
    }

    static void removeAll(Zone a) {
        DeviceZoneLog.where { zone == a }.deleteAll()
    }

    static void removeAll(Device d) {
        DeviceZone.where { device == d }.deleteAll()
    }
}
