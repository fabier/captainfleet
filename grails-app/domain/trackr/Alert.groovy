package trackr

import com.vividsolutions.jts.geom.Geometry

class Alert extends BaseEntity {

    /**
     * Zone définissant où se situe l'alerte
     */
    Geometry geometry

    /**
     * Si on inverse la zone d'alerte
     */
    Boolean isGeometryInverted

    /**
     * Etat actuel de l'alerte
     */
    Boolean isRaised

    static constraints = {
        geometry nullable: false
        isGeometryInverted nullable: true
        isRaised nullable: true
    }
}
