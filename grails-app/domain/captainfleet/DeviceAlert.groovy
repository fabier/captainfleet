package captainfleet

class DeviceAlert extends BaseDomain {

    /**
     * Boitier pour lequel une alerte est levée
     */
    Device device

    /**
     * Alerte levée pour ce device
     */
    Alert alert

    /**
     * Indique si l'alerte est actuellement levée pour ce boitier et cette alerte
     */
    Boolean isRaised

    static hasMany = [deviceAlertLogs: DeviceAlertLog]

    static constraints = {
        device nullable: false, unique: ['alert']
        isRaised nullable: true
    }

    static void removeAll(Alert a) {
        DeviceAlert.where { alert == a }.deleteAll()
    }

    static void removeAll(Device d) {
        DeviceAlert.where { device == d }.deleteAll()
    }
}
