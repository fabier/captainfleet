package captainfleet

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class CommonController {

    static defaultAction = "index"

    MapService mapService
    DeviceService deviceService
    UtilService utilService
    MapMarkerIconService mapMarkerIconService
    FrameService frameService

    SpringSecurityService springSecurityService

    def index() {
        User user = springSecurityService.currentUser
        List<Device> devices = deviceService.getDevicesByUser(user)
        utilService.sortBaseEntities(devices)

        Map<Device, Frame> deviceFrameMap = new HashMap<>()

        if (devices == null || devices.isEmpty()) {
            // Pas de device pour cet utilisateur, il devra ajouter ses boitiers.
        } else {
            // Plusieurs devices
            devices.each {
                Frame lastFrame = frameService.getLastFrameWithGeolocationWithin24Hours(it)
                deviceFrameMap.put(it, lastFrame)
            }
        }

        MapOptions mapOptions = mapService.buildFromDeviceFrameMap(deviceFrameMap)

        render view: "index", model: [
                devices             : devices,
                mapOptions          : mapOptions,
                deviceFrameMap      : deviceFrameMap,
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }
}
