package trackr

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.io.WKTWriter
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class AlertController {

    static defaultAction = "index"

    SpringSecurityService springSecurityService
    MapService mapService
    DeviceService deviceService
    AlertService alertService
    UtilService utilService

    def index() {
        User user = springSecurityService.currentUser
        List<Alert> alerts = alertService.getAlertsForUser(user)
        utilService.sortBaseEntities(alerts)

        int offset = params.offset ?: 0
        int max = params.max ?: 10
        int totalCount = alerts.size()
        int endIndex = Math.min(totalCount, offset + max)

        render view: "index", model: [
                results   : alerts.subList(offset, endIndex),
                totalCount: totalCount
        ]
    }

    def create() {
        User user = springSecurityService.currentUser
        def devices = deviceService.getByUser(user)
        MapOptions mapOptions = mapService.buildFromDevicesUsingLastFrame(devices)
        render view: "create", model: [
                mapOptions: mapOptions
        ]
    }

    def search() {

    }

    def createAlertUsingGeometry() {
        User user = springSecurityService.currentUser
        log.info(params.wkt)
        WKTReader wktReader = new WKTReader()
        Geometry geometry = wktReader.read(params.wkt)
        Alert alert = new Alert(
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
        def devices = deviceService.getByUser(user)
        MapOptions mapOptions = mapService.buildFromDevicesUsingLastFrame(devices)
        String wktGeometry = new WKTWriter().write(alert.geometry)
        render view: "show", model: [
                alert      : alert,
                mapOptions : mapOptions,
                wktGeometry: wktGeometry
        ]
    }
}
