package captainfleet

import grails.transaction.Transactional

@Transactional
class DeviceZoneLogService {

    DeviceZoneService deviceZoneService
    UtilService utilService

    List<DeviceZoneLog> getDeviceZoneLogsByDeviceAndZone(Device device, Zone zone) {
        DeviceZoneLog.findAllByDeviceAndZone(device, zone)
    }

    List<DeviceZoneLogAggregate> getDeviceZoneLogAggregatesByDeviceAndZone(Device device, Zone zone) {
        DeviceZoneLogAggregate.findAllByDeviceAndZone(device, zone)
    }

    List<DeviceZoneLogAggregate> getDeviceZoneLogAggregatesForZones(List<Zone> zones) {
        List<DeviceZoneLogAggregate> deviceZoneLogAggregates = new ArrayList<>()
        zones.each {
            deviceZoneLogAggregates.addAll(DeviceZoneLogAggregate.findAllByZone(it))
        }
        return deviceZoneLogAggregates
    }

    List<DeviceZoneLogAggregate> getDeviceZoneLogAggregatesForZonesWithinPeriod(List<Zone> zones, Date dateLowerBound, Date dateUpperBound) {
        List<DeviceZoneLogAggregate> deviceZoneLogAggregates = new ArrayList<>()
        zones.each {
            deviceZoneLogAggregates.addAll(DeviceZoneLogAggregate.findAllByZoneAndDateCreatedBetween(it, dateLowerBound, dateUpperBound))
        }
        return deviceZoneLogAggregates
    }
}
