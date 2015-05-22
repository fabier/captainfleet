package trackr

class UserAlert extends BaseDomain {
    /**
     * Utilisateur qui peut voir cette alerte
     */
    User user

    /**
     * Alerte de l'utilisateur
     */
    Alert alert

    static constraints = {
        user nullable: false, unique: ['alert']
    }

    static UserAlert create(User user, Alert device, boolean flush = false) {
        def instance = new UserAlert(user: user, device: device)
        instance.save(flush: flush)
    }
}
