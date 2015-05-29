package trackr

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.io.WKTWriter
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

import java.util.regex.Pattern

@Secured("hasRole('ROLE_USER')")
class AlertController {

    static defaultAction = "index"

    SpringSecurityService springSecurityService
    MapService mapService
    DeviceService deviceService
    AlertService alertService
    UtilService utilService
    FrameService frameService
    UserService userService

    def index() {
        User user = springSecurityService.currentUser
        List<Alert> alerts = alertService.getAlertsForUser(user)
        if (params.name) {
            Pattern pattern = Pattern.compile(".*${params.name}.*", Pattern.CASE_INSENSITIVE)
            alerts = alerts.findAll {
                pattern.matcher(it.name ?: "").matches()
            }
        }
        utilService.sortBaseEntities(alerts)

        int offset = params.offset ?: 0
        int max = params.max ?: 10
        int totalCount = alerts.size()
        int endIndex = Math.min(totalCount, offset + max)
        alerts = alerts.subList(offset, endIndex)

        alerts.each {
            it.area = alertService.getArea(it)
        }

        render view: "index", model: [
                alerts    : alerts,
                totalCount: totalCount
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

    def createAlertUsingGeometry() {
        User user = springSecurityService.currentUser
        WKTReader wktReader = new WKTReader()
        Geometry geometry = wktReader.read(params.wkt)
        Alert alert = new Alert(
                name: params.name,
                isGeometryInverted: params.isGeometryInverted,
                geometry: geometry,
                creator: user
        )
        alert.save(flush: true)
        UserAlert.create(user, alert)
        redirect action: "show", id: alert.id
    }

    def show(long id) {
        Alert alert = Alert.get(id)
        User user = springSecurityService.currentUser
        String wktGeometry

        List<Frame> frames = frameService.getFramesForAlert(alert)
        MapOptions mapOptions = mapService.buildFromFrames(frames)
        mapOptions.boundingBox = alert.geometry.getEnvelopeInternal()

//        if (alert.isGeometryInverted) {
//            LineString exteriorRing = ((Polygon) alert.geometry).getExteriorRing()
//            LinearRing worldForOpenLayers = gisUtilService.getWorldAsLinearRing()
//            Polygon outerPolygon = new GeometryFactory().createPolygon(worldForOpenLayers, (LinearRing[]) [exteriorRing].toArray())
//            wktGeometry = new WKTWriter().write(outerPolygon)
//        } else {
        wktGeometry = new WKTWriter().write(alert.geometry)
//        }

        render view: "show", model: [
                alert      : alert,
                mapOptions : mapOptions,
                wktGeometry: wktGeometry
        ]
    }

    def update(long id) {
        Alert alert = Alert.get(id)

        WKTReader wktReader = new WKTReader()
        Geometry geometry
        if (params.wkt) {
            geometry = wktReader.read(params.wkt)
            alert.geometry = geometry
        }

        bindData(alert, params, [include: ["name", "isGeometryInverted"]])
        alert.save()

        flash.success = "Enregistrement effectué"
        redirect action: "show", id: alert.id
    }

    def delete(long id) {
        Alert alert = Alert.get(id)
        if (alert) {
            UserAlert.removeAll(alert)
            alert.delete()
        }
        redirect action: "index"
    }

    def devices(long id) {
        Alert alert = Alert.get(id)
        List<DeviceAlert> deviceAlerts = alertService.getDeviceAlerts(alert)
        render view: "devices", model: [
                alert       : alert,
                deviceAlerts: deviceAlerts
        ]
    }

    def updateDeviceState(long id) {
        Alert alert = Alert.get(id)
        User user = springSecurityService.currentUser
//        List<DeviceAlert> deviceAlerts = alertService.getDeviceAlerts(alert)
        List<Device> devices = deviceService.getDevicesByUser(user)
        devices.each {
            DeviceAlert deviceAlert = DeviceAlert.findOrSaveByDeviceAndAlert(it, alert)
            Frame frame = frameService.getLastFrameWithGeolocation(it)
            if (frame) {
                deviceAlert.isRaised = alertService.isAlertRaised(alert, frame)
                deviceAlert.save()
            }
        }
        redirect action: "devices", id: id
    }
}
