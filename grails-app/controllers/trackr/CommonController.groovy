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
        MapOptions mapOptions
        if (devices == null || devices.isEmpty()) {
            def devicesToFillMap = Device.findAllByDeviceState(DeviceState.NORMAL)
            mapOptions = mapService.buildFromDevicesUsingRandomFrame(devicesToFillMap)
        } else {
            mapOptions = mapService.buildFromDevicesUsingLastFrame(devices)
        }
        render view: "index", model: [
                devices   : devices,
                mapOptions: mapOptions
        ]
    }
}
