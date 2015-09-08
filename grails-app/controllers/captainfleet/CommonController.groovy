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
        Map<Device, Frame> deviceLastFrameMap = new HashMap<>()

        devices?.each {
            deviceLastFrameMap.put(it, frameService.getLastFrame(it))
            deviceFrameMap.put(it, frameService.getLastFrameWithGeolocationWithin24Hours(it))
        }

        MapOptions mapOptions = mapService.buildFromDeviceFrameMap(deviceFrameMap)

        render view: "index", model: [
                devices             : devices,
                mapOptions          : mapOptions,
                deviceFrameMap      : deviceFrameMap,
                deviceLastFrameMap  : deviceLastFrameMap,
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }
}
