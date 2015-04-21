package trackr

import grails.transaction.Transactional
import org.geotools.geometry.Envelope2D
import org.geotools.geometry.GeometryBuilder
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.opengis.geometry.Envelope
import org.opengis.geometry.aggregate.MultiPoint

@Transactional
class MapService {

    DecoderService decoderService
    DeviceService deviceService
    GeometryBuilder geometryBuilder

    MapOptions buildFromDevicesUsingLastFrame(def devices) {
        Set<org.opengis.geometry.primitive.Point> points = new HashSet<>()
        devices.each {
            Frame lastFrame = deviceService.lastFrame(it)
            DataDecoded dataDecoded = decoderService.tryDecode(lastFrame.data)
            if (dataDecoded) {
                points.add(getGeometryBuilder().createPoint(dataDecoded.longitude, dataDecoded.latitude))
            }
        }
        if (points.isEmpty()) {
            return defaultMapOptions()
        } else {
            return buildUsingPoints(points)
        }
    }

    MapOptions buildUsingPoints(Set<org.opengis.geometry.primitive.Point> points) {
        Envelope2D boundingBox = new Envelope2D()
        MultiPoint multiPoint = getGeometryBuilder().createMultiPoint(points)
        Envelope envelope = multiPoint.getEnvelope()
        boundingBox.setFrame(envelope.getMinimum(0), envelope.getMinimum(1),
                envelope.getMaximum(0) - envelope.getMinimum(0), envelope.getMaximum(1) - envelope.getMinimum(1))
        MapMarkerStyle markerStyle = new MapMarkerStyle(
                path: "markers/car.png"
        )
        return new MapOptions(
                boundingBox: boundingBox,
                mapMarkerLayers: [new MapMarkerLayer(
                        mapMarkerStyle: markerStyle,
                        points: points
                )]
        )
    }

    MapOptions defaultMapOptions() {
        Envelope2D boundingBox = new Envelope2D()
        boundingBox.setFrame(-10.0, 30.0, 30.0, 30.0)
        new MapOptions(
                boundingBox: null,
                mapMarkerLayers: null
        )
    }

    MapOptions buildFromDevicesUsingRandomFrame(List<Device> devices) {
        Set<org.opengis.geometry.primitive.Point> points = new HashSet<>()
        devices.each {
            Frame randomFrame = deviceService.randomFrame(it)
            DataDecoded dataDecoded = decoderService.tryDecode(randomFrame.data)
            points.add(getGeometryBuilder().createPoint(dataDecoded.longitude, dataDecoded.latitude))
        }
        if (points.isEmpty()) {
            return defaultMapOptions()
        } else {
            return buildUsingPoints(points)
        }
    }

    GeometryBuilder getGeometryBuilder() {
        if (geometryBuilder == null) {
            geometryBuilder = new GeometryBuilder(DefaultGeographicCRS.WGS84);
        }
        geometryBuilder
    }
}
