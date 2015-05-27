package trackr

class DeviceAlert extends BaseDomain {

    /**
     * Boitier pour lequel une alerte est levée
     */
    Device device

    /**
     * Alerte levée pour ce device
     */
    Alert alert

    static constraints = {
    }
}
