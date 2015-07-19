package captainfleet.admin

import captainfleet.MapMarkerIcon
import org.springframework.security.access.annotation.Secured
import captainfleet.Device
import captainfleet.DeviceService
import captainfleet.Frame

@Secured("hasRole('ROLE_ADMIN')")
class AdminDeviceController {

    static defaultAction = "search"

    DeviceService deviceService

    def search() {
        def devices = Device.createCriteria().list {
            order("name", "asc")
            maxResults(10)
        }
        render view: "search", model: [
                results   : devices,
                totalCount: Device.count()
        ]
    }

    def show(long id) {
        Device device = Device.get(id)
        def frames = Frame.findAllByDeviceAndDuplicate(device, false,
                [max: params.max ?: 10, offset: params.offset ?: 0, sort: "dateCreated", order: "desc"])
        int totalCount = Frame.countByDeviceAndDuplicate(device, false)
        def mapMarkerIcons = MapMarkerIcon.all
        render view: "show", model: [
                device          : device,
                results         : frames,
                mapMarkerIcons  : mapMarkerIcons,
                mapMarkerIconIds: mapMarkerIcons*.id,
                totalCount      : totalCount
        ]
    }

    def deviceSearch() {
        def devices = Device.findAllByNameIlike("%${params.name ?: ""}%",
                [max: params.max ?: 10, offset: params.offset ?: 0, sort: "name", order: "asc"])
        int totalCount = Device.countByNameIlike("%${params.name ?: ""}%")
        render view: "search", model: [
                results   : devices,
                totalCount: totalCount
        ]
    }

    def update(long id) {
        Device device = Device.get(id)
        bindData(device, params, [include: ["name", "code"]])
        device.save()
        flash.message = "Enregistrement effectué"
        redirect action: "show", id: id
    }

    def randomCode(long id) {
        Device device = Device.get(id)
        if (deviceService.generateNewUniqueCodeForDevice(device)) {
            device.save()
            flash.message = "Enregistrement effectué"
            redirect action: "show", id: id
        } else {
            flash.error = "Impossible de générer un nouveau code pour ce boitier. Veuillez réessayer"
            redirect action: "show", id: id
        }
    }
}