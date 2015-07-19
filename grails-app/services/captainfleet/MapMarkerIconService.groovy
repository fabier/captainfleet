package captainfleet

import grails.transaction.Transactional

@Transactional
class MapMarkerIconService {

    MapMarkerIcon getDefault() {
        MapMarkerIcon.findByIsDefault(true) ?: MapMarkerIcon.first()
    }

    def setDefaultIcon(MapMarkerIcon mapMarkerIcon) {
        MapMarkerIcon.findAllByIsDefault(true).each {
            it.isDefault = false
            it.save()
        }
        mapMarkerIcon.isDefault = true
        mapMarkerIcon.save()
    }
}
