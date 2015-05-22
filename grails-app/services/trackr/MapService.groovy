package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Envelope
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.MultiPoint
import grails.transaction.Transactional

@Transactional
class MapService {

    DeviceService deviceService
    GeometryFactory geometryFactory
    StationService stationService
    FrameService frameService

    MapOptions buildFromDevicesUsingLastFrame(List<Device> devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame lastFrame = frameService.lastFrameWithGeolocation(it)
            if (lastFrame?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(lastFrame.location as com.vividsolutions.jts.geom.Point)
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
            return defaultMapOptions()
        }
    }

    MapOptions defaultMapOptions() {
        Set<com.vividsolutions.jts.geom.Point> staticPoints = new HashSet()
        staticPoints.add(getGeometryFactory().createPoint(new Coordinate(0.0, -80.0)))
        staticPoints.add(getGeometryFactory().createPoint(new Coordinate(0.0, 80.0)))
        return buildUsingPoints(staticPoints)
    }

    MapOptions buildFromDevicesUsingRandomFrame(List<Device> devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame randomFrameWithGeolocation = frameService.randomFrameWithGeolocation(it)
            if (randomFrameWithGeolocation?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(randomFrameWithGeolocation.location as com.vividsolutions.jts.geom.Point)
            }
        }
        return buildUsingPoints(points)
    }

    GeometryFactory getGeometryFactory() {
        if (geometryFactory == null) {
            geometryFactory = new GeometryFactory()
        }
        geometryFactory
    }

    MapOptions buildFromStation(Station station) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        List<Frame> frames = stationService.getFramesWithGeolocation(station)
        frames?.each {
            if (it?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(it.location as com.vividsolutions.jts.geom.Point)
            }
        }
        return buildUsingPoints(points)
    }

    MapOptions buildFromDevicesUsingAllFrames(List<Device> devices) {
        buildFromDevicesUsingAllFrames(devices, -1)
    }

    MapOptions buildFromDevicesUsingAllFrames(List<Device> devices, int max) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            List<Frame> frames = frameService.getFramesForDeviceWithGeolocation(it, max)
            frames?.each {
                if (it?.location instanceof com.vividsolutions.jts.geom.Point) {
                    points.add(it.location as com.vividsolutions.jts.geom.Point)
                }
            }
        }
        return buildUsingPoints(points)
    }
}
