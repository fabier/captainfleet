package captainfleet

class DeviceZoneLog extends BaseDomain {

    /**
     * Boitier et Zone concernées
     */
    DeviceZone deviceZone

    /**
     * Indique si le boitier est dans la zone pour ce log
     */
    Boolean isRaised

    static hasOne = [deviceZone: DeviceZone]

    static constraints = {
        isRaised nullable: true
    }
}
