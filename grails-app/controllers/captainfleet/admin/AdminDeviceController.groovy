package captainfleet.admin

import captainfleet.*
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class AdminDeviceController {

    static defaultAction = "search"

    DeviceService deviceService
    MapMarkerIconService mapMarkerIconService

    def search() {
        List<Device> devices = Device.createCriteria().list {
            order("name", "asc")
            maxResults(10)
        }
        render view: "search", model: [
                results             : devices,
                totalCount          : Device.count(),
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }

    def show(long id) {
        Device device = Device.get(id)
        def frames = Frame.findAllByDeviceAndDuplicate(device, false,
                [max: params.max ?: 10, offset: params.offset ?: 0, sort: "dateCreated", order: "desc"])
        int totalCount = Frame.countByDeviceAndDuplicate(device, false)
        def mapMarkerIcons = MapMarkerIcon.all
        render view: "show", model: [
                device             : device,
                results            : frames,
                deviceMapMarkerIcon: device.mapMarkerIcon ?: mapMarkerIconService.getDefault(),
                mapMarkerIcons     : mapMarkerIcons,
                mapMarkerIconIds   : mapMarkerIcons*.id,
                totalCount         : totalCount
        ]
    }

    def deviceSearch() {
        int max = params.max ? Integer.parseInt(params.max) : 10
        int offset = params.offset ? Integer.parseInt(params.offset) : 0

        int totalCount = Device.createCriteria().count() {
            if (params.name) {
                or {
                    ilike "name", "%${params.name}%"
                    ilike "sigfoxId", "%${params.name}%"
                }
            }
        }
        def devices = Device.createCriteria().list([max: max, offset: offset]) {
            if (params.name) {
                or {
                    ilike "name", "%${params.name}%"
                    ilike "sigfoxId", "%${params.name}%"
                }
            }
            order "name", "asc"
        }
        render view: "search", model: [
                results             : devices,
                totalCount          : totalCount,
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }

    def create() {
        def mapMarkerIcons = MapMarkerIcon.all
        def defaultMapMarker = mapMarkerIconService.getDefault()
        render view: "create", model: [mapMarkerIcons: mapMarkerIcons, defaultMapMarker: defaultMapMarker]
    }

    def save() {
        Device device = new Device(
                name: params.name ?: params.sigfoxId,
                sigfoxId: params.sigfoxId,
                mapMarkerIcon: MapMarkerIcon.get(params.mapMarkerIcon)
        )

        if (device.validate()) {
            deviceService.generateNewUniqueCodeForDevice(device)
            device.save()
            flash.message = "Enregistrement effectué"
            redirect action: "create"
        } else {
            flash.warning = "Impossible de créer ce boitier.<br/>" +
                    "Vérifiez que le code sigfox est bon, et que le nom est bien renseigné.<br/>" +
                    "Vérifiez aussi que le code n'existe pas déjà."
            redirect action: "create", params: params
        }
    }

    def update(long id) {
        Device device = Device.get(id)
        bindData(device, params, [include: ["name", "code"]])
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(params.mapMarkerIcon)
        if (mapMarkerIcon != null) {
            if (device.mapMarkerIcon == null && mapMarkerIconService.getDefault() == mapMarkerIcon) {
                // Pas de modification, on continue d'utiliser l'icone par défaut
            } else {
                device.mapMarkerIcon = mapMarkerIcon
            }
        }
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
