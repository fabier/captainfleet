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

    boolean isAlertRaised(Alert alert, Frame frame) {
        if (!alert.isGeometryInverted) {
            // On teste si le boitier est dans la zone de l'alerte
            return alert.geometry.contains(frame.location)
        } else {
            // Si la géométrie est inversée, on considère que l'alerte est levée lorsque
            // le boitier est "en dehors" de la zone de l'alerte
            return !alert.geometry.contains(frame.location)
        }
    }
}
