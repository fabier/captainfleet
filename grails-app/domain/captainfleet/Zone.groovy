package captainfleet

import com.vividsolutions.jts.geom.Geometry

class Zone extends BaseEntity {

    /**
     * Géométrie de la zone
     */
    Geometry geometry

    /**
     * Etat actuel de la zone
     */
    Boolean isRaised

    /**
     * Aire de la zone en m²
     */
    Double areaInSquareMeters

    static transients = ['areaInSquareMeters']

    static hasMany = [deviceZoneLogAggregates: DeviceZoneLogAggregate]

    static constraints = {
        geometry nullable: false
        isRaised nullable: true
    }
}
