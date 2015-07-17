package captainfleet

import grails.transaction.Transactional

@Transactional
class DeviceAlertService {

    List<DeviceAlert> getDeviceAlertsByAlert(Alert alert) {
        DeviceAlert.findAllByAlert(alert)
    }
}
