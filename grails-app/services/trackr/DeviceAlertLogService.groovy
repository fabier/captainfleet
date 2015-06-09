package trackr

import grails.transaction.Transactional

@Transactional
class DeviceAlertLogService {

    List<DeviceAlertLog> sortByMostRecentFirst(List<DeviceAlertLog> deviceAlertLogs) {
        deviceAlertLogs.sort { a, b ->
            -a.dateCreated.compareTo(b.dateCreated)
        }
    }
}
