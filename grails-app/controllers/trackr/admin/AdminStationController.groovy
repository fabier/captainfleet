package trackr.admin

import org.springframework.security.access.annotation.Secured
import trackr.*

@Secured("hasRole('ROLE_ADMIN')")
class AdminStationController {

    static defaultAction = "search"

    StationService stationService
    FrameService frameService
    MapService mapService

    def search() {
        def stations = Station.createCriteria().list {
            order("sigfoxId", "asc")
            maxResults(10)
        }
        render view: "search", model: [
                results   : stations,
                totalCount: Station.count()
        ]
    }

    def edit(long id) {
        Station station = Station.get(id)
        List<Frame> frames = frameService.getFramesForStationWithGeolocation(station, [max: params.max ?: 10, offset: params.offset ?: 0, sort: "dateCreated", order: "desc"])
        int totalCount = frameService.countFramesForStationWithGeolocation(station)

        MapOptions mapOptions = mapService.buildFromFrames(frames)
        render view: "edit", model: [
                station   : station,
                results   : frames,
                totalCount: totalCount,
                mapOptions: mapOptions
        ]
    }

    def stationSearch() {
        def stations = Station.findAllBySigfoxIdIlike("%${params.name ?: ""}%",
                [max: params.max ?: 10, offset: params.offset ?: 0, sort: "sigfoxId", order: "asc"])
        int totalCount = Station.countBySigfoxIdIlike("%${params.name ?: ""}%")
        render view: "search", model: [
                results   : stations,
                totalCount: totalCount
        ]
    }

    def update(long id) {
        Station station = Station.get(id)
        bindData(station, params, [include: ["name"]])
        station.save()
        flash.message = "Enregistrement effectué"
        redirect action: "edit", id: id
    }
}
