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
        Map<Device, Set<com.vividsolutions.jts.geom.Point>> deviceAndPointsMap = new HashMap<>()
        devices.each {
            Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
            Frame lastFrame = frameService.getLastFrameWithGeolocation(it)
            if (lastFrame?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(lastFrame.location as com.vividsolutions.jts.geom.Point)
            }
            deviceAndPointsMap.put(it, points)
        }
        return buildFromDevicePointsMap(deviceAndPointsMap)
    }

    MapOptions buildFromDevicesUsingLastFrameWithin24Hours(List<Device> devices) {
        Map<Device, Set<com.vividsolutions.jts.geom.Point>> deviceAndPointsMap = new HashMap<>()
        devices.each {
            Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
            Frame lastFrame = frameService.getLastFrameWithGeolocationWithin24Hours(it)
            if (lastFrame?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(lastFrame.location as com.vividsolutions.jts.geom.Point)
            }
            deviceAndPointsMap.put(it, points)
        }
        return buildFromDevicePointsMap(deviceAndPointsMap)
    }

    MapOptions buildFromPointsNoMarker(Set<com.vividsolutions.jts.geom.Point> points) {
        if (points != null && !points.isEmpty()) {
            MultiPoint multiPoint = new GeometryFactory().createMultiPoint(GeometryFactory.toPointArray(points))
            Envelope envelope = multiPoint.getEnvelopeInternal()
            return new MapOptions(
                    boundingBox: envelope
            )
        } else {
            return defaultMapOptions()
        }
    }

    MapOptions buildFromPointsWithDefaultMarker(Set<com.vividsolutions.jts.geom.Point> points) {
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

    MapOptions buildFromDeviceAndFrame(Device device, Frame frame) {
        if (frame?.location instanceof com.vividsolutions.jts.geom.Point) {
            buildFromDeviceAndPoint(device, frame.location as com.vividsolutions.jts.geom.Point)
        } else {
            return defaultMapOptions()
        }
    }

    MapOptions buildFromDeviceFrameMap(Map<Device, Frame> deviceFrameMap) {
        Map<Device, Set<com.vividsolutions.jts.geom.Point>> mapDevicesAndPoints = new HashMap<>()
        deviceFrameMap.each {
            if (it.value?.location instanceof com.vividsolutions.jts.geom.Point) {
                com.vividsolutions.jts.geom.Point point = it.value?.location as com.vividsolutions.jts.geom.Point
                mapDevicesAndPoints.put(it.key, Collections.singleton(point))
            }
        }
        buildFromDevicePointsMap(mapDevicesAndPoints)
    }

    MapOptions buildFromDeviceAndPoint(Device device, com.vividsolutions.jts.geom.Point point) {
        buildFromDeviceAndPoints(device, Collections.singleton(point))
    }

    MapOptions buildFromDeviceAndPoints(Device device, Set<com.vividsolutions.jts.geom.Point> points) {
        buildFromDevicePointsMap(Collections.singletonMap(device, points))
    }

    MapOptions buildFromDevicePointsMap(Map<Device, Set<com.vividsolutions.jts.geom.Point>> mapDevicesAndPoints) {
        if (mapDevicesAndPoints != null && !mapDevicesAndPoints.isEmpty()) {
            Set<com.vividsolutions.jts.geom.Point> allPoints = new HashSet<>()

            def mapMarkerLayers = []
            mapDevicesAndPoints.each {
                allPoints.addAll(it.value)
                mapMarkerLayers.add(new MapMarkerLayer(
                        mapMarkerStyle: getMapMarkerStyleForDevice(it.key),
                        points: it.value
                ))
            }

            MultiPoint multiPoint = new GeometryFactory().createMultiPoint(GeometryFactory.toPointArray(allPoints))
            Envelope envelope = multiPoint.getEnvelopeInternal()

            return new MapOptions(
                    boundingBox: envelope,
                    mapMarkerLayers: mapMarkerLayers
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
        return buildFromPointsNoMarker(staticPoints)
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

    MapMarkerStyle getMapMarkerStyleForDevice(Device device) {
        if (device && device.mapMarkerIcon) {
            getMapMarkerStyleForMapMarkerIcon(device.mapMarkerIcon)
        } else {
            defaultMapMarkerStyle()
        }
    }

    MapMarkerStyle getMapMarkerStyleForMapMarkerIcon(MapMarkerIcon mapMarkerIcon) {
        if (mapMarkerIcon) {
            new MapMarkerStyle(
                    path: grailsLinkGenerator.link(controller: "mapMarker", action: "index", id: mapMarkerIcon.id),
                    anchorX: mapMarkerIcon.anchorX,
                    anchorY: mapMarkerIcon.anchorY
            )
        } else {
            defaultMapMarkerStyle()
        }
    }


    MapOptions buildFromDevicesUsingRandomFrame(List<Device> devices) {
        Map<Device, Set<com.vividsolutions.jts.geom.Point>> deviceAndPointsMap = new HashMap<>()
        devices.each {
            Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
            Frame randomFrameWithGeolocation = frameService.getRandomFrameWithGeolocation(it)
            if (randomFrameWithGeolocation?.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(randomFrameWithGeolocation.location as com.vividsolutions.jts.geom.Point)
            }
            deviceAndPointsMap.put(it, points)
        }
        return buildFromDevicePointsMap(deviceAndPointsMap)
    }

    MapOptions buildFromStation(Station station) {
        return buildFromFrames(frameService.getFramesForStationWithGeolocation(station))
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
        return buildFromPointsWithDefaultMarker(points)
    }
}
