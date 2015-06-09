package trackr

import com.vividsolutions.jts.geom.Geometry

class Alert extends BaseEntity {

    /**
     * Zone définissant où se situe l'alerte
     */
    Geometry geometry

    /**
     * Etat actuel de l'alerte
     */
    Boolean isRaised

    /**
     * Aire de la zone d'alerte en m²
     */
    Double area

    static transients = ['area']

    static constraints = {
        geometry nullable: false
        isRaised nullable: true
    }
}
