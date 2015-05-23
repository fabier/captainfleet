package trackr

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTWriter

class Alert extends BaseEntity {

    Geometry geometry

    static constraints = {
        geometry nullable: false
    }
}
