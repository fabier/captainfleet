package trackr

class Point extends BaseDomain {

    // Latitude
    double latitude

    // Longitude
    double longitude

    static constraints = {
        latitude nullable: false
        longitude nullable: false
    }
}
