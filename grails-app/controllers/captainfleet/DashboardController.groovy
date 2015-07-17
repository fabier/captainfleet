package captainfleet

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
        def deviceGraphSignalDataMap = [:]
        def deviceGraphRSSIDataMap = [:]
        devices.each {
            List<Frame> frames = frameService.getLastFramesSinceLastDays(it, 4)
            if (frames != null && !frames.isEmpty()) {
                framesMap.put(it, frames)
            }
            Frame lastFrame = frameService.getLastFrame(it)
            lastFrameMap.put(it, lastFrame)

            def deviceGraphData = []
            def deviceGraphTemperatureData = []
            def deviceGraphSignalData = []
            def deviceGraphRSSIData = []
            frames.each {
                deviceGraphData.add([it.time, it.frameExtra?.superCapacitorVoltage, it.frameExtra?.solarArrayVoltage])
                deviceGraphTemperatureData.add([it.time, it.frameExtra?.currentTemperature, it.frameExtra?.averageTemperature, it.frameExtra?.maxTemperature, it.frameExtra?.minTemperature])
                deviceGraphSignalData.add([it.time, it.avgSignal, it.snr])
                deviceGraphRSSIData.add([it.time, it.rssi])
            }
            deviceGraphDataMap.put(it, deviceGraphData)
            deviceGraphTemperatureDataMap.put(it, deviceGraphTemperatureData)
            deviceGraphSignalDataMap.put(it, deviceGraphSignalData)
            deviceGraphRSSIDataMap.put(it, deviceGraphRSSIData)
        }
        def deviceGraphDataColumns = [['date', 'Date'], ['number', 'Tension Super capacité (V)'], ['number', 'Tension Panneau solaire (V)']]
        def deviceGraphTemperatureDataColumns = [['date', 'Date'], ['number', 'T° instantannée (°C)'], ['number', 'T° moy (°C)'], ['number', 'T° max(°C)'], ['number', 'T° min(°C)']]
        def deviceGraphSignalDataColumns = [['date', 'Date'], ['number', 'avgSignal'], ['number', 'SNR']]
        def deviceGraphRSSIDataColumns = [['date', 'Date'], ['number', 'RSSI']]

        devices.sort { a, b ->
            List<Frame> aFrames = framesMap.get(a)
            boolean aFramesNullOrEmpty = aFrames == null || aFrames.isEmpty()
            List<Frame> bFrames = framesMap.get(b)
            boolean bFramesNullOrEmpty = bFrames == null || bFrames.isEmpty()
            if (aFramesNullOrEmpty && bFramesNullOrEmpty) {
                return 0
            } else if (aFramesNullOrEmpty) {
                return 1
            } else if (bFramesNullOrEmpty) {
                return -1
            } else {
                return utilService.compareBaseEntity(a, b)
            }
        }

        render view: "devices", model: [devices                      : devices, framesMap: framesMap, lastFrameMap: lastFrameMap,
                                        deviceGraphDataMap           : deviceGraphDataMap, deviceGraphDataColumns: deviceGraphDataColumns,
                                        deviceGraphTemperatureDataMap: deviceGraphTemperatureDataMap, deviceGraphTemperatureDataColumns: deviceGraphTemperatureDataColumns,
                                        deviceGraphSignalDataMap     : deviceGraphSignalDataMap, deviceGraphSignalDataColumns: deviceGraphSignalDataColumns,
                                        deviceGraphRSSIDataMap       : deviceGraphRSSIDataMap, deviceGraphRSSIDataColumns: deviceGraphRSSIDataColumns
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

