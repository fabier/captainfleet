package captainfleet

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DeviceAlertController {

    DeviceAlertLogService deviceAlertLogService

    def show(long id) {
        DeviceAlert deviceAlert = DeviceAlert.get(id)
        List<DeviceAlertLog> deviceAlertLogs = deviceAlertLogService.getDeviceAlertLogsByDeviceAlert(deviceAlert)
        deviceAlertLogs = deviceAlertLogService.sortByMostRecentFirst(deviceAlertLogs)
        render view: "show", model: [
                deviceAlert    : deviceAlert,
                alert          : deviceAlert.alert,
                device         : deviceAlert.device,
                deviceAlertLogs: deviceAlertLogs
        ]
    }
}
