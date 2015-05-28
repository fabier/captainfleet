package trackr

class DeviceAlertController {

    def show(long id) {
        DeviceAlert deviceAlert = DeviceAlert.get(id)
        List<DeviceAlertLog> deviceAlertLogs = DeviceAlertLog.findAllByDeviceAlert(deviceAlert)
        render view: "show", model: [
                alert          : deviceAlert.alert,
                deviceAlertLogs: deviceAlertLogs
        ]
    }
}
