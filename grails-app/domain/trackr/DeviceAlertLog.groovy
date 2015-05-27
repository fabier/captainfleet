package trackr

class DeviceAlertLog extends BaseDomain {

    /**
     * Boitier et Alerte concernés
     */
    DeviceAlert deviceAlert

    /**
     * Indique si l'alerte est actuellement levée pour ce boitier et cette alerte
     */
    Boolean isRaised

    static hasOne = [deviceAlert: DeviceAlert]

    static constraints = {
        isRaised nullable: true
    }
}
