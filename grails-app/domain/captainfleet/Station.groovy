package captainfleet

import com.vividsolutions.jts.geom.Geometry

class Station extends BaseEntity {

    // Identifiant sigfox
    String sigfoxId

    /**
     * Position de la station (valeur estimée en fonction des différentes qualités de signal)
     */
    Geometry position

    static constraints = {
        sigfoxId nullable: false, unique: true
        position nullable: true
    }

    int frameCount() {
        Frame.countByStation(this)
    }
}
