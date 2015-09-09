package captainfleet

class UserZone extends BaseDomain {

    /**
     * Utilisateur qui peut voir cette zone
     */
    User user

    /**
     * Zone de l'utilisateur
     */
    Zone zone

    static constraints = {
        user nullable: false, unique: ['zone']
    }

    static UserZone create(User user, Zone zone, boolean flush = false) {
        def instance = new UserZone(user: user, zone: zone)
        instance.save(flush: flush)
    }

    static void removeAll(Zone z) {
        UserZone.where { zone == z }.deleteAll()
    }

    static void removeAll(User u) {
        UserZone.where { user == u }.deleteAll()
    }
}
