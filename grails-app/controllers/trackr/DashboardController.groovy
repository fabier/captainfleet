package trackr

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
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
        def deviceGraphDataMap = [:]
        def deviceGraphTemperatureDataMap = [:]
        devices.each {
            List<Frame> frames = frameService.getLastFramesSinceLastDays(it, 4)
            if (frames != null && !frames.isEmpty()) {
                framesMap.put(it, frames)
            }
            Frame lastFrame = frameService.getLastFrame(it)
            lastFrameMap.put(it, lastFrame)

            def deviceGraphData = []
            def deviceGraphTemperatureData = []
            frames.each {
                deviceGraphData.add([it.time, it.frameExtra?.superCapacitorVoltage, it.frameExtra?.solarArrayVoltage])
                deviceGraphTemperatureData.add([it.time, it.frameExtra?.currentTemperature, it.frameExtra?.averageTemperature, it.frameExtra?.maxTemperature, it.frameExtra?.minTemperature])
            }
            deviceGraphDataMap.put(it, deviceGraphData)
            deviceGraphTemperatureDataMap.put(it, deviceGraphTemperatureData)
        }
        def deviceGraphDataColumns = [['date', 'Date'], ['number', 'Tension Super capacité (V)'], ['number', 'Tension Panneau solaire (V)']]
        def deviceGraphTemperatureDataColumns = [['date', 'Date'], ['number', 'T° instantannée (°C)'], ['number', 'T° moy (°C)'], ['number', 'T° max(°C)'], ['number', 'T° min(°C)']]

        render view: "devices", model: [devices                      : devices, framesMap: framesMap, lastFrameMap: lastFrameMap,
                                        deviceGraphDataMap           : deviceGraphDataMap, deviceGraphDataColumns: deviceGraphDataColumns,
                                        deviceGraphTemperatureDataMap: deviceGraphTemperatureDataMap, deviceGraphTemperatureDataColumns: deviceGraphTemperatureDataColumns
        ]
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

