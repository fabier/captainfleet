package captainfleet

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DashboardController {

    FrameService frameService
    DeviceService deviceService
    SpringSecurityService springSecurityService
    UtilService utilService
    ZoneService zoneService

    def index() {
        redirect action: "devices"
    }

    def devices() {
        User user = springSecurityService.currentUser
        List<Device> devices = deviceService.getDevicesByUser(user)
        utilService.sortBaseEntities(devices)
        def framesMap = [:]
        def lastFrameMap = [:]
        def deviceGraphVoltageDataMap = [:]
        def deviceGraphTemperatureDataMap = [:]
        def deviceGraphRSSIDataMap = [:]
        devices.each {
            List<Frame> frames = frameService.getLastFramesSinceLastDays(it, 4)
            if (frames != null && !frames.isEmpty()) {
                framesMap.put(it, frames)
            }
            Frame lastFrame = frameService.getLastFrame(it)
            lastFrameMap.put(it, lastFrame)

            def deviceGraphVoltageData = []
            def deviceGraphTemperatureData = []
            def deviceGraphRSSIData = []
            frames.each {
                FrameExtra frameExtra = it.frameExtra
                if (frameExtra?.superCapacitorVoltage || frameExtra?.superCapacitorVoltage) {
                    deviceGraphVoltageData.add([it.time, frameExtra?.superCapacitorVoltage, frameExtra?.solarArrayVoltage])
                }
                if (frameExtra?.currentTemperature || frameExtra?.averageTemperature || frameExtra?.maxTemperature || frameExtra?.minTemperature) {
                    deviceGraphTemperatureData.add([it.time, frameExtra?.currentTemperature, frameExtra?.averageTemperature, frameExtra?.maxTemperature, frameExtra?.minTemperature])
                }
                if (it.rssiMax || it.rssiAvg || it.rssiMin) {
                    deviceGraphRSSIData.add([it.time, it.rssiMax, it.rssiAvg, it.rssiMin])
                }
            }
            deviceGraphVoltageDataMap.put(it, deviceGraphVoltageData)
            deviceGraphTemperatureDataMap.put(it, deviceGraphTemperatureData)
            deviceGraphRSSIDataMap.put(it, deviceGraphRSSIData)
        }
        def deviceGraphDataColumns = [['date', 'Date'], ['number', 'Tension Super capacité (V)'], ['number', 'Tension Panneau solaire (V)']]
        def deviceGraphTemperatureDataColumns = [['date', 'Date'], ['number', 'T° instantannée (°C)'], ['number', 'T° moy (°C)'], ['number', 'T° max(°C)'], ['number', 'T° min(°C)']]
        def deviceGraphRSSIDataColumns = [['date', 'Date'], ['number', 'RSSI (Max)'], ['number', 'RSSI (Moyen)'], ['number', 'RSSI (Min)']]

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
                                        deviceGraphDataMap           : deviceGraphVoltageDataMap, deviceGraphDataColumns: deviceGraphDataColumns,
                                        deviceGraphTemperatureDataMap: deviceGraphTemperatureDataMap, deviceGraphTemperatureDataColumns: deviceGraphTemperatureDataColumns,
                                        deviceGraphRSSIDataMap       : deviceGraphRSSIDataMap, deviceGraphRSSIDataColumns: deviceGraphRSSIDataColumns
        ]
    }

    def zones() {
        User user = springSecurityService.currentUser

        List<Zone> zones = zoneService.getZonesForUser(user)
        List<Device> devices = deviceService.getDevicesByUser(user)
        def stateZoneDeviceMap = [:]

        def calendar = Calendar.getInstance()
        // Avant une semaine, on considère qu'on ne sait plus où est l'objet.
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        Date lowerBoundDate = calendar.getTime()

        zones.each {
            Zone zone = it
            int inZoneCount = 0
            int outOfZoneCount = 0
            int unknownStateCount = 0
            devices.count {
                Device device = it
                DeviceZone deviceZone = DeviceZone.findByDeviceAndZoneAndLastUpdatedGreaterThan(device, zone, lowerBoundDate)
                if (deviceZone == null) {
                    unknownStateCount++
                } else {
                    if (deviceZone.isRaised) {
                        inZoneCount++
                    } else {
                        outOfZoneCount++
                    }
                }
            }
            stateZoneDeviceMap.put(zone, [inZoneCount, outOfZoneCount, unknownStateCount])
        }

        render view: "zones", model: [zones: zones, stateZoneDeviceMap: stateZoneDeviceMap]
    }
}

