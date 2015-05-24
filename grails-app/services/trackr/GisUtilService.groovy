package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.LinearRing
import grails.transaction.Transactional

@Transactional
class GisUtilService {

    LinearRing getWorldAsLinearRing() {
        new GeometryFactory().createLinearRing((Coordinate[]) [
                new Coordinate(-180.0d, -90.0d, 0d),
                new Coordinate(-180.0d, 90.0d, 0d),
                new Coordinate(180.0d, 90.0d, 0d),
                new Coordinate(180.0d, -90.0d, 0d),
                new Coordinate(-180.0d, -90.0d, 0d)
        ].toArray())
    }

    /**
     * Openlayers demande à avoir latitude min = -85°, latitude max = 85°
     * @return
     */
    LinearRing getWorldAsLinearRingForOpenLayers() {
        new GeometryFactory().createLinearRing((Coordinate[]) [
                new Coordinate(-180.0d, -85.0d, 0d),
                new Coordinate(-180.0d, 85.0d, 0d),
                new Coordinate(180.0d, 85.0d, 0d),
                new Coordinate(180.0d, -85.0d, 0d),
                new Coordinate(-180.0d, -85.0d, 0d)
        ].toArray())
    }
}
