package trackr

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class DashboardController {

    FrameService frameService
    DeviceService deviceService
    SpringSecurityService springSecurityService
    UtilService utilService
    AlertService alertService

    def index() {
        redirect action: "devices"
    }

    def devices() {
        User user = springSecurityService.currentUser
        List<Device> devices = deviceService.getDevicesByUser(user)
        utilService.sortBaseEntities(devices)
        def framesMap = [:]
        def lastFrameMap = [:]
        devices.each {
            List<Frame> frames = frameService.getLastFramesSinceLastDays(it, 7)
            if (frames != null && !frames.isEmpty()) {
                framesMap.put(it, frames)
            }
            Frame lastFrame = frameService.getLastFrame(it)
            lastFrameMap.put(it, lastFrame)
        }

        render view: "devices", model: [devices: devices, framesMap: framesMap, lastFrameMap: lastFrameMap]
    }

    def alerts() {
        User user = springSecurityService.currentUser

        List<Alert> alerts = alertService.getAlertsForUser(user)
        List<Device> devices = deviceService.getDevicesByUser(user)
        def stateAlertDeviceMap = [:]

        def calendar = Calendar.getInstance()
        // Avant une semaine, on considère qu'on ne sait plus où est l'objet.
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        Date lowerBoundDate = calendar.getTime()

        alerts.each {
            Alert alert = it
            int inZoneCount = 0
            int outOfZoneCount = 0
            int unknownStateCount = 0
            devices.count {
                Device device = it
                DeviceAlert deviceAlert = DeviceAlert.findByDeviceAndAlertAndLastUpdatedGreaterThan(device, alert, lowerBoundDate)
                if (deviceAlert == null) {
                    unknownStateCount++
                } else {
                    if (deviceAlert.isRaised) {
                        inZoneCount++
                    } else {
                        outOfZoneCount++
                    }
                }
            }
            stateAlertDeviceMap.put(alert, [inZoneCount, outOfZoneCount, unknownStateCount])
        }

        render view: "alerts", model: [alerts: alerts, stateAlertDeviceMap: stateAlertDeviceMap]
    }
}

