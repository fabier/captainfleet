package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class CommonController {

    static defaultAction = "index"

    def springSecurityService
    DecoderService decoderService
    MapService mapService
    DeviceService deviceService
    UtilService utilService

    def index() {
        User user = springSecurityService.currentUser
        List<Device> devices = deviceService.getByUser(user)
        utilService.sortBaseEntities(devices)

        if (devices == null || devices.isEmpty()) {
            // Pas de device pour cet utilisateur, on lui affiche des valeurs "aléatoires"
            def devicesToFillMap = Device.findAllByDeviceState(DeviceState.NORMAL)
            MapOptions mapOptions = mapService.buildFromDevicesUsingRandomFrame(devicesToFillMap)
            render view: "index", model: [
                    devices   : devices,
                    mapOptions: mapOptions
            ]
        } else {
            // Plusieurs devices
            MapOptions mapOptions = mapService.buildFromDevicesUsingLastFrame(devices)
            render view: "index", model: [
                    devices   : devices,
                    mapOptions: mapOptions
            ]
        }
    }
}
