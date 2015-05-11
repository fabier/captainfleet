package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class CommonController {

    static defaultAction = "index"

    def springSecurityService
    DecoderService decoderService
    MapService mapService
    DeviceService deviceService

    def index() {
        User user = springSecurityService.currentUser
        def devices = UserDevice.findAllByUser(user)*.device
        if (devices == null || devices.isEmpty()) {
            // Pas de device pour cet utilisateur, on lui affiche des valeurs "aléatoires"
            def devicesToFillMap = Device.findAllByDeviceState(DeviceState.NORMAL)
            MapOptions mapOptions = mapService.buildFromDevicesUsingRandomFrame(devicesToFillMap)
            render view: "index", model: [
                    devices   : devices,
                    mapOptions: mapOptions
            ]
        } else if (devices.size() == 1) {
            // Un seul device, on redirige directement vers la page du device en question
            Device device = devices.get(0)
            redirect controller: "device", action: "map", id: device.id
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
