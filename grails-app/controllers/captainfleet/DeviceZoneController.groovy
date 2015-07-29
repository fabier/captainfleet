package captainfleet

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DeviceZoneController {

    DeviceZoneLogService deviceZoneLogService

    def show(long id) {
        DeviceZone deviceZone = DeviceZone.get(id)
        List<DeviceZoneLog> deviceZoneLogs = deviceZoneLogService.getDeviceZoneLogsByDeviceZone(deviceZone)
        deviceZoneLogs = deviceZoneLogService.sortByMostRecentFirst(deviceZoneLogs)
        render view: "show", model: [
                deviceZone    : deviceZone,
                zone          : deviceZone.zone,
                device        : deviceZone.device,
                deviceZoneLogs: deviceZoneLogs
        ]
    }
}
