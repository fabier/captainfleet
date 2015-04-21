package trackr

class Point {

    // Latitude
    double latitude

    // Longitude
    double longitude

    // Date de création en base
    Date dateCreated

    // Date de dernière modification en base
    Date lastUpdated

    static constraints = {
        latitude nullable: false
        longitude nullable: false
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
