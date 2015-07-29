package captainfleet

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.io.WKTWriter
import grails.plugin.springsecurity.SpringSecurityService
import org.apache.commons.lang3.time.DateUtils
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
    DeviceAlertLogService deviceAlertLogService
    DeviceAlertService deviceAlertService
    ParserService parserService

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
            it.areaInSquareMeters = alertService.getArea(it)
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

        List<Device> devices = deviceService.getDevicesByUser(user)
        utilService.sortBaseEntities(devices)

        Map<Device, Frame> deviceFrameMap = new HashMap<>()

        devices?.each {
            Frame lastFrame = frameService.getLastFrameWithGeolocationWithin24Hours(it)
            deviceFrameMap.put(it, lastFrame)
        }

        MapOptions mapOptions = mapService.buildFromDeviceFrameMap(deviceFrameMap)
        mapOptions.boundingBox = alert.geometry.getEnvelopeInternal()

        wktGeometry = new WKTWriter().write(alert.geometry)

        render view: "show", model: [
                alert      : alert,
                mapOptions : mapOptions,
                wktGeometry: wktGeometry
        ]
    }

    def logs() {
        List<DeviceAlertLog> deviceAlertLogsAggregated = new ArrayList<>()

        Date date = parserService.tryParseDate(params.date) ?: new Date()
        Date dateUpperBound = DateUtils.ceiling(date, Calendar.DAY_OF_MONTH)
        Date dateLowerBound = DateUtils.addDays(dateUpperBound, -7)

        User user = springSecurityService.currentUser
        List<Alert> alerts = alertService.getAlertsForUser(user)
        alerts?.each {
            List<DeviceAlert> deviceAlerts = deviceAlertService.getDeviceAlertsByAlert(it)
            deviceAlerts?.each {
                List<DeviceAlertLog> localDeviceAlertLogs = deviceAlertLogService.getDeviceAlertLogsByDeviceAlert(it, dateLowerBound, dateUpperBound)
                boolean isRaised = localDeviceAlertLogs?.first()?.isRaised
                localDeviceAlertLogs?.each {
                    if (isRaised != it.isRaised) {
                        // Changement d'état, on ajoute ce log à l'aggrégation
                        deviceAlertLogsAggregated.add(it)
                        isRaised = it.isRaised
                    }
                }
            }
        }
        List<DeviceAlertLog> deviceAlertLogs = deviceAlertLogService.sortByMostRecentFirst(deviceAlertLogsAggregated)

        render view: "logs", model: [
                alerts         : alerts,
                deviceAlertLogs: deviceAlertLogs
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

        bindData(alert, params, [include: ["name"]])
        alert.save()

        flash.success = "Enregistrement effectué"
        redirect action: "show", id: alert.id
    }

    def delete(long id) {
        Alert alert = Alert.get(id)
        if (alert) {
            UserAlert.removeAll(alert)
            DeviceAlert.removeAll(alert)
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
                deviceAlert.isRaised = alertService.isFrameWithinAlertGeometry(alert, frame)
                deviceAlert.save()
            }
        }
        redirect action: "devices", id: id
    }
}
