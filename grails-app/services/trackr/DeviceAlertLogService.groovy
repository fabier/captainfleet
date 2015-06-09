package trackr

import grails.transaction.Transactional

@Transactional
class DeviceAlertLogService {

    DeviceAlertService deviceAlertService

    List<DeviceAlertLog> sortByMostRecentFirst(List<DeviceAlertLog> deviceAlertLogs) {
        deviceAlertLogs.sort { a, b ->
            -a.dateCreated.compareTo(b.dateCreated)
        }
    }

    List<DeviceAlertLog> getDeviceAlertLogsByAlert(Alert alert) {
        List<DeviceAlert> deviceAlerts = deviceAlertService.getDeviceAlertsByAlert(alert)
        return DeviceAlertLog.createCriteria().list {
            inList("deviceAlert", deviceAlerts)
        }
    }

    List<DeviceAlertLog> getDeviceAlertLogsByDeviceAlert(DeviceAlert deviceAlert) {
        DeviceAlertLog.findAllByDeviceAlert(deviceAlert)
    }

    List<DeviceAlertLog> getDeviceAlertLogsByDeviceAlert(DeviceAlert deviceAlert, Date dateLowerBound, Date dateUpperBound) {
        DeviceAlertLog.findAllByDeviceAlert(deviceAlert)
        DeviceAlertLog.withCriteria {
            eq("deviceAlert", deviceAlert)
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }
}
