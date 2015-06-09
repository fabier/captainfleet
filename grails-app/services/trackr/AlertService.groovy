package trackr

import grails.transaction.Transactional

@Transactional
class AlertService {

    // Auto inject SessionFactory we can use
    // to get the current Hibernate session.
    def sessionFactory

    List<Alert> getAlertsForUser(User user) {
        UserAlert.findAllByUser(user)*.alert
    }

    List<Alert> getAlertsForUser(User user, String name) {
        UserAlert.findAllByUser(user)*.alert
    }

    double getArea(Alert alert) {
        // Get the current Hiberante session.
        final session = sessionFactory.currentSession
        final sqlQuery = session.createSQLQuery("SELECT ST_Area(ST_GeogFromText(ST_AsText(a.geometry))) FROM Alert a WHERE a.id = :id")
        return sqlQuery.setLong("id", alert.id).setMaxResults(1).uniqueResult()
    }

    List<DeviceAlert> getDeviceAlerts(Alert alert) {
        DeviceAlert.findAllByAlert(alert)
    }

    boolean isFrameWithinAlertGeometry(Alert alert, Frame frame) {
        // On teste si le boitier est dans la zone de l'alerte
        return alert.geometry.contains(frame.location)
    }
}
