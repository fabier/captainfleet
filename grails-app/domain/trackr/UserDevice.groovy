package trackr

class UserDevice {
    /**
     * Utilisateur qui peut voir ce device
     */
    User user

    /**
     * Device de l'utilisateur
     */
    Device device

    // Date de création en base
    Date dateCreated

    // Date de dernière modification en base
    Date lastUpdated

    static constraints = {
        user nullable: false, unique: ['device']
    }

    static UserDevice create(User user, Device device, boolean flush = false) {
        def instance = new UserDevice(user: user, device: device)
        instance.save(flush: flush, insert: true)
        instance
    }
}
