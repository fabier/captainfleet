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
        def devices = UserDevice.findAllByUser(user)*.device?.sort { a, b ->
            if (a.name == null) {
                1
            } else if (b.name == null) {
                -1
            } else {
                a.name.compareTo(b.name)
            }
        }
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
