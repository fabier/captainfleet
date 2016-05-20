package captainfleet

class Device extends BaseEntity {

    // Identifiant sigfox
    String sigfoxId

    // Etat du device
    DeviceState deviceState

    // Famille de device
    DeviceFamily deviceFamily

    // Type de device
    DeviceType deviceType

    // Code unique permettant d'associer un device à son compte
    String code

    /**
     * Marqueur de carte spécifique pour ce terminal.
     */
    MapMarkerIcon mapMarkerIcon

    static hasMany = [
            frames                 : Frame,
            deviceZoneLogAggregates: DeviceZoneLogAggregate,
            userDevices            : UserDevice
    ]

    static constraints = {
        sigfoxId nullable: false, unique: true
        deviceState nullable: true
        code nullable: true, unique: true
        deviceFamily nullable: true
        deviceType nullable: true
        mapMarkerIcon nullable: true
    }
}
