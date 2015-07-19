package captainfleet.admin

import captainfleet.Device
import captainfleet.DeviceService
import captainfleet.Frame
import captainfleet.MapMarkerIcon
import captainfleet.MapMarkerIconService
import org.springframework.security.access.annotation.Secured
import org.springframework.web.multipart.commons.CommonsMultipartFile

@Secured("hasRole('ROLE_ADMIN')")
class AdminMapMarkerIconController {

    static defaultAction = "search"

    MapMarkerIconService mapMarkerIconService

    def search() {
        def mapMarkerIcons = MapMarkerIcon.createCriteria().list {
            order("name", "asc")
            maxResults(10)
        }
        render view: "search", model: [
                results   : mapMarkerIcons,
                totalCount: MapMarkerIcon.count()
        ]
    }

    def show(long id) {
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(id)
        render view: "show", model: [
                mapMarkerIcon: mapMarkerIcon
        ]
    }

    def mapMarkerIconSearch() {
        def mapMarkerIcons = MapMarkerIcon.findAllByNameIlike("%${params.name ?: ""}%",
                [max: params.max ?: 10, offset: params.offset ?: 0, sort: "name", order: "asc"])
        int totalCount = MapMarkerIcon.countByNameIlike("%${params.name ?: ""}%")
        render view: "search", model: [
                results   : mapMarkerIcons,
                totalCount: totalCount
        ]
    }

    def update(long id) {
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(id)
        bindData(mapMarkerIcon, params, [include: ["name"]])
        mapMarkerIcon.save()
        flash.message = "Enregistrement effectué"
        redirect action: "show", id: id
    }

    def uploadData() {
        def file = request.getFile('file')
        if (file instanceof CommonsMultipartFile) {
            if (file.isEmpty()) {
                flash.warning = "File cannot be empty"
            } else {
                MapMarkerIcon mapMarkerIcon = new MapMarkerIcon(
                        data: file.bytes,
                        name: file.originalFilename,
                        filename: file.originalFilename
                )
                mapMarkerIcon.save()
            }
            render text: "OK"
        } else {
            flash.warning = "Internal error during upload"
            response.sendError(500)
            render text: "KO"
        }
    }

    def removeAll() {
        MapMarkerIcon.all.each {
            it.delete()
        }
        redirect action: "search"
    }

    def setAsDefault(long id){
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(id)
        mapMarkerIconService.setDefaultIcon(mapMarkerIcon)
        redirect action: "show", id: id
    }
}
