package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DeviceAlertController {

    def show(long id) {
        DeviceAlert deviceAlert = DeviceAlert.get(id)
        List<DeviceAlertLog> deviceAlertLogs = DeviceAlertLog.findAllByDeviceAlert(deviceAlert)
        render view: "show", model: [
                deviceAlert    : deviceAlert,
                alert          : deviceAlert.alert,
                device         : deviceAlert.device,
                deviceAlertLogs: deviceAlertLogs
        ]
    }
}
