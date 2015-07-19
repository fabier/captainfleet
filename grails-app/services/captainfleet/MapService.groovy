package captainfleet

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Envelope
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.MultiPoint
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

@Transactional
class MapService {

    StationService stationService
    FrameService frameService
    MapMarkerIconService mapMarkerIconService

    LinkGenerator grailsLinkGenerator

    MapOptions buildFromDevicesUsingLastFrame(List<Device> devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame lastFrame = frameService.getLastFrameWithGeolocation(it)
            if (lastFrame?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(lastFrame.location as com.vividsolutions.jts.geom.Point)
            }
        }
        return buildFromPoints(points)
    }

    MapOptions buildFromPoints(Set<com.vividsolutions.jts.geom.Point> points) {
        if (points != null && !points.isEmpty()) {
            MultiPoint multiPoint = new GeometryFactory().createMultiPoint(GeometryFactory.toPointArray(points))
            Envelope envelope = multiPoint.getEnvelopeInternal()
            MapMarkerStyle markerStyle = defaultMapMarkerStyle()
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
        GeometryFactory geometryFactory = new GeometryFactory()
        staticPoints.add(geometryFactory.createPoint(new Coordinate(10.0, 20.0)))
        staticPoints.add(geometryFactory.createPoint(new Coordinate(10.0, 60.0)))
        return buildFromPoints(staticPoints)
    }

    MapMarkerStyle defaultMapMarkerStyle() {
        MapMarkerIcon mapMarkerIcon = mapMarkerIconService.getDefault()
        if (mapMarkerIcon) {
            new MapMarkerStyle(
                    path: grailsLinkGenerator.link(controller: "mapMarker", action: "index", id: mapMarkerIcon.id),
                    anchorX: mapMarkerIcon.anchorX,
                    anchorY: mapMarkerIcon.anchorY
            )
        } else {
            new MapMarkerStyle(
                    path: "markers/car.png",
                    anchorX: 0.5f,
                    anchorY: 1.0f
            )
        }
    }

    MapOptions buildFromDevicesUsingRandomFrame(List<Device> devices) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        devices.each {
            Frame randomFrameWithGeolocation = frameService.randomFrameWithGeolocation(it)
            if (randomFrameWithGeolocation?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(randomFrameWithGeolocation.location as com.vividsolutions.jts.geom.Point)
            }
        }
        return buildFromPoints(points)
    }

    MapOptions buildFromStation(Station station) {
        return buildFromFrames(stationService.getFramesWithGeolocation(station))
    }

    MapOptions buildFromDevicesUsingAllFrames(List<Device> devices) {
        buildFromDevicesUsingAllFrames(devices, -1)
    }

    MapOptions buildFromDevicesUsingAllFrames(List<Device> devices, int max) {
        Set<Frame> frameSet = new HashSet<>()
        devices.each {
            List<Frame> frames = frameService.getFramesForDeviceWithGeolocation(it, max)
            frameSet.addAll(frames)
        }
        return buildFromFrames(frameSet)
    }

    MapOptions buildFromFrames(Collection<Frame> frames) {
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        frames?.each {
            if (it?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(it.location as com.vividsolutions.jts.geom.Point)
            }
        }
        return buildFromPoints(points)
    }
}
