package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Envelope
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.MultiPoint
import grails.transaction.Transactional

@Transactional
class MapService {

    DecoderService decoderService
    DeviceService deviceService
    GeometryFactory geometryFactory

    MapOptions buildFromDevicesUsingLastFrame(def devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame lastFrame = deviceService.lastFrame(it)
            FrameData_V1 frameData = decoderService.tryDecode(lastFrame)
            if (frameData) {
                points.add(getGeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude)))
            }
        }
        if (points.isEmpty()) {
            return defaultMapOptions()
        } else {
            return buildUsingPoints(points)
        }
    }

    MapOptions buildUsingPoints(Set<com.vividsolutions.jts.geom.Point> points) {
        MultiPoint multiPoint = getGeometryFactory().createMultiPoint(GeometryFactory.toPointArray(points))
        Envelope envelope = multiPoint.getEnvelopeInternal()
        MapMarkerStyle markerStyle = new MapMarkerStyle(
                path: "markers/car.png"
        )
        return new MapOptions(
                boundingBox: envelope,
                mapMarkerLayers: [new MapMarkerLayer(
                        mapMarkerStyle: markerStyle,
                        points: points
                )]
        )
    }

    MapOptions defaultMapOptions() {
        new MapOptions(
                boundingBox: null,
                mapMarkerLayers: null
        )
    }

    MapOptions buildFromDevicesUsingRandomFrame(List<Device> devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame randomFrame = deviceService.randomFrame(it)
            FrameData_V1 frameData = decoderService.tryDecode(randomFrame)
            points.add(getGeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude)))
        }
        if (points.isEmpty()) {
            return defaultMapOptions()
        } else {
            return buildUsingPoints(points)
        }
    }

    GeometryFactory getGeometryFactory() {
        if (geometryFactory == null) {
            geometryFactory = new GeometryFactory()
        }
        geometryFactory
    }
}
