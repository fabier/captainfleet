package captainfleet

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.io.WKTWriter
import grails.plugin.springsecurity.SpringSecurityService
import org.apache.commons.lang3.time.DateUtils
import org.springframework.security.access.annotation.Secured

import java.util.regex.Pattern

@Secured("hasRole('ROLE_USER')")
class ZoneController {

    static defaultAction = "index"

    SpringSecurityService springSecurityService
    MapService mapService
    DeviceService deviceService
    ZoneService zoneService
    UtilService utilService
    FrameService frameService
    UserService userService
    DeviceZoneLogService deviceZoneLogService
    DeviceZoneService deviceZoneService
    ParserService parserService

    def index() {
        User user = springSecurityService.currentUser
        List<Zone> zones = zoneService.getZonesForUser(user)

        // On affiche aussi les logs sur une semaine
        def calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        Date dateLowerBound = calendar.getTime()
        List<DeviceZoneLogAggregate> deviceZoneLogAggregates = deviceZoneLogService.getDeviceZoneLogAggregatesForZonesWithinPeriod(zones, dateLowerBound, new Date())
        deviceZoneLogAggregates = utilService.sortByMostRecentFirst(deviceZoneLogAggregates) as List<DeviceZoneLogAggregate>

        if (params.name) {
            Pattern pattern = Pattern.compile(".*${params.name}.*", Pattern.CASE_INSENSITIVE)
            zones = zones.findAll {
                pattern.matcher(it.name ?: "").matches()
            }
        }
        utilService.sortBaseEntities(zones)

        int offset = params.offset ?: 0
        int max = params.max ?: 10
        int totalCount = zones.size()
        int endIndex = Math.min(totalCount, offset + max)
        zones = zones.subList(offset, endIndex)

        zones.each {
            it.areaInSquareMeters = zoneService.getArea(it)
        }

        render view: "index", model: [
                zones                  : zones,
                totalCount             : totalCount,
                deviceZoneLogAggregates: deviceZoneLogAggregates
        ]
    }

    def create() {
        User user = springSecurityService.currentUser
        def devices = deviceService.getDevicesByUser(user)
        MapOptions mapOptions = mapService.buildFromDevicesUsingLastFrame(devices)
        render view: "create", model: [
                mapOptions: mapOptions
        ]
    }

    def createZoneUsingGeometry() {
        User user = springSecurityService.currentUser
        WKTReader wktReader = new WKTReader()
        Geometry geometry = wktReader.read(params.wkt)
        Zone zone = new Zone(
                name: params.name,
                geometry: geometry,
                creator: user
        )
        zone.save(flush: true)
        UserZone.create(user, zone)
        redirect action: "show", id: zone.id
    }

    def show(long id) {
        Zone zone = Zone.get(id)
        User user = springSecurityService.currentUser
        String wktGeometry

        List<Device> devices = deviceService.getDevicesByUser(user)
        utilService.sortBaseEntities(devices)

        Map<Device, Frame> deviceFrameMap = new HashMap<>()

        devices?.each {
            Frame lastFrame = frameService.getLastFrameWithGeolocationWithin24Hours(it)
            deviceFrameMap.put(it, lastFrame)
        }

        MapOptions mapOptions = mapService.buildFromDeviceFrameMap(deviceFrameMap)
        mapOptions.boundingBox = zone.geometry.getEnvelopeInternal()

        wktGeometry = new WKTWriter().write(zone.geometry)

        render view: "show", model: [
                zone       : zone,
                mapOptions : mapOptions,
                wktGeometry: wktGeometry
        ]
    }

    def update(long id) {
        Zone zone = Zone.get(id)

        WKTReader wktReader = new WKTReader()
        Geometry geometry
        if (params.wkt) {
            geometry = wktReader.read(params.wkt)
            zone.geometry = geometry
        }

        bindData(zone, params, [include: ["name"]])
        zone.save()

        flash.success = "Enregistrement effectué"
        redirect action: "show", id: zone.id
    }

    def delete(long id) {
        Zone zone = Zone.get(id)
        if (zone) {
            UserZone.removeAll(zone)
            DeviceZone.removeAll(zone)
            DeviceZoneLog.removeAll(zone)
            zone.delete()
        }
        redirect action: "index"
    }

    def devices(long id) {
        Zone zone = Zone.get(id)
        List<DeviceZone> deviceZones = zoneService.getDeviceZones(zone)
        render view: "devices", model: [
                zone       : zone,
                devices    : deviceZones*.device,
                deviceZones: deviceZones
        ]
    }

    def updateDeviceState(long id) {
        Zone zone = Zone.get(id)
        User user = springSecurityService.currentUser
//        List<DeviceZone> deviceZones = zoneService.getDeviceZones(zone)
        List<Device> devices = deviceService.getDevicesByUser(user)
        devices.each {
            DeviceZone deviceZone = DeviceZone.findOrSaveByDeviceAndZone(it, zone)
            Frame frame = frameService.getLastFrameWithGeolocation(it)
            if (frame) {
                deviceZone.isRaised = zoneService.isFrameWithinZoneGeometry(zone, frame)
                deviceZone.save()
            }
        }
        redirect action: "devices", id: id
    }

    def deviceLog(long zoneId, long deviceId) {
        Zone zone = Zone.get(zoneId)
        Device device = Device.get(deviceId)
        DeviceZone deviceZone = DeviceZone.findByDeviceAndZone(device, zone)

        List<DeviceZoneLog> deviceZoneLogs = deviceZoneLogService.getDeviceZoneLogsByDeviceAndZone(device, zone)
        deviceZoneLogs = utilService.sortByMostRecentFirst(deviceZoneLogs)

        List<DeviceZoneLogAggregate> deviceZoneLogAggregates = deviceZoneLogService.getDeviceZoneLogAggregatesByDeviceAndZone(device, zone)
        deviceZoneLogs = utilService.sortByMostRecentFirst(deviceZoneLogs)

        render view: "deviceLog", model: [
                deviceZone             : deviceZone,
                zone                   : zone,
                device                 : device,
                deviceZoneLogs         : deviceZoneLogs,
                deviceZoneLogAggregates: deviceZoneLogAggregates
        ]
    }

    def logs() {
        User user = springSecurityService.currentUser
        List<Zone> zones = zoneService.getZonesForUser(user)
        List<DeviceZoneLogAggregate> deviceZoneLogAggregates = deviceZoneLogService.getDeviceZoneLogAggregatesForZones(zones)
        deviceZoneLogAggregates = utilService.sortByMostRecentFirst(deviceZoneLogAggregates) as List<DeviceZoneLogAggregate>

        render view: "logs", model: [
                zones                  : zones,
                deviceZoneLogAggregates: deviceZoneLogAggregates
        ]
    }
}
