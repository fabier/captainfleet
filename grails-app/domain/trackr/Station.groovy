package trackr

class Station {

    // Nom de la station
    String name

    // Identifiant sigfox
    String sigfoxId

    // Date de création en base
    Date dateCreated

    // Date de dernière modification en base
    Date lastUpdated

    static constraints = {
        name nullable: true
        sigfoxId nullable: false, unique: true
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
