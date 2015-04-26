package trackr

class Station extends BaseEntity {

    // Identifiant sigfox
    String sigfoxId

    static constraints = {
        sigfoxId nullable: false, unique: true
    }
}
