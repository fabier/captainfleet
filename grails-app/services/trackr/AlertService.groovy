package trackr

import grails.transaction.Transactional

@Transactional
class AlertService {

    List<Alert> getAlertsForUser(User user) {
        UserAlert.findAllByUser(user)*.alert
    }

    List<Alert> getAlertsForUser(User user, String name) {
        UserAlert.findAllByUser(user)*.alert
    }
}
