package trackr

import grails.transaction.Transactional

@Transactional
class AlertService {

    List<Alert> getAlertsForUser(User user) {
        UserAlert.findAllByUser(user)*.alert
    }
}
