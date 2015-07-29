package captainfleet

import grails.transaction.Transactional

@Transactional
class DeviceZoneLogService {

    DeviceZoneService deviceZoneService

    List<DeviceZoneLog> sortByMostRecentFirst(List<DeviceZoneLog> deviceZoneLogs) {
        deviceZoneLogs.sort { a, b ->
            -a.dateCreated.compareTo(b.dateCreated)
        }
    }

    List<DeviceZoneLog> getDeviceZoneLogsByZone(Zone zone) {
        List<DeviceZone> deviceZones = deviceZoneService.getDeviceZonesByZone(zone)
        return DeviceZoneLog.createCriteria().list {
            inList("deviceZone", deviceZones)
        }
    }

    List<DeviceZoneLog> getDeviceZoneLogsByDeviceZone(DeviceZone deviceZone) {
        DeviceZoneLog.findAllByDeviceZone(deviceZone)
    }

    List<DeviceZoneLog> getDeviceZoneLogsByDeviceZone(DeviceZone deviceZone, Date dateLowerBound, Date dateUpperBound) {
        DeviceZoneLog.findAllByDeviceZone(deviceZone)
        DeviceZoneLog.withCriteria {
            eq("deviceZone", deviceZone)
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }
}
