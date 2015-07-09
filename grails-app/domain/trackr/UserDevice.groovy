package trackr

class UserDevice extends BaseDomain {
    /**
     * Utilisateur qui peut voir ce device
     */
    User user

    /**
     * Device de l'utilisateur
     */
    Device device

    static constraints = {
        user nullable: false, unique: ['device']
    }

    static UserDevice create(User user, Device device, boolean flush = false) {
        def instance = new UserDevice(user: user, device: device)
        instance.save(flush: flush)
    }

    static UserDevice remove(User user, Device device, boolean flush = false) {
        def userDevice = UserDevice.findByUserAndDevice(user, device)
        if (userDevice != null) {
            userDevice.delete(flush: flush)
        }
    }
}
