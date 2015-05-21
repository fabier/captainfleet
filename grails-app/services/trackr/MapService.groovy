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
            Frame lastFrame = deviceService.lastFrameWithGeolocation(it)
            FrameData frameData = decoderService.tryDecode(lastFrame)
            if (frameData) {
                if (frameData?.hasGeolocationData()) {
                    points.add(getGeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude)))
                }
            }
        }
        return buildUsingPoints(points)
    }

    MapOptions buildUsingPoints(Set<com.vividsolutions.jts.geom.Point> points) {
        if (points != null && !points.isEmpty()) {
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
        } else {
            Set<com.vividsolutions.jts.geom.Point> staticPoints = new HashSet()
            staticPoints.add(getGeometryFactory().createPoint(new Coordinate(0.0, -80.0)))
            staticPoints.add(getGeometryFactory().createPoint(new Coordinate(0.0, 80.0)))
            return buildUsingPoints(staticPoints)
        }
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
            Frame randomFrameWithGeolocation = deviceService.randomFrameWithGeolocation(it)
            if (randomFrameWithGeolocation != null) {
                FrameData frameData = decoderService.tryDecode(randomFrameWithGeolocation)
                points.add(getGeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude)))
            }
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
