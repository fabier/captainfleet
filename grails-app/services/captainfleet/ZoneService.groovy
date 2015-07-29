package captainfleet

import grails.transaction.Transactional

@Transactional
class ZoneService {

    // Auto inject SessionFactory we can use
    // to get the current Hibernate session.
    def sessionFactory

    List<Zone> getZonesForUser(User user) {
        UserZone.findAllByUser(user)*.zone
    }

    List<Zone> getZonesForUser(User user, String name) {
        UserZone.findAllByUser(user)*.zone
    }

    double getArea(Zone zone) {
        // Get the current Hiberante session.
        final session = sessionFactory.currentSession
        final sqlQuery = session.createSQLQuery("SELECT ST_Area(ST_GeogFromText(ST_AsText(z.geometry))) FROM Zone z WHERE z.id = :id")
        return sqlQuery.setLong("id", zone.id).setMaxResults(1).uniqueResult()
    }

    List<DeviceZone> getDeviceZones(Zone zone) {
        DeviceZone.findAllByZone(zone)
    }

    boolean isFrameWithinZoneGeometry(Zone zone, Frame frame) {
        // On teste si le boitier est dans la zone
        return zone.geometry.contains(frame.location)
    }
}
