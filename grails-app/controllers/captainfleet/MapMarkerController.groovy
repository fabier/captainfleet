package captainfleet

import org.springframework.security.access.annotation.Secured

@Secured("permitAll")
class MapMarkerController {
    static defaultAction = "index"

    def index(long id) {
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(id)
        render file: mapMarkerIcon.data, contentType: 'image/png'
    }
}
