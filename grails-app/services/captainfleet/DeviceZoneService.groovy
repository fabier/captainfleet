package captainfleet

import grails.transaction.Transactional

@Transactional
class DeviceZoneService {

    List<DeviceZone> getDeviceZonesByZone(Zone zone) {
        DeviceZone.findAllByZone(zone)
    }
}
