package trackr.admin

import org.springframework.security.access.annotation.Secured
import trackr.Station

@Secured("hasRole('ROLE_ADMIN')")
class AdminStationController {

    static defaultAction = "search"

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

    def show(long id) {
        Station station = Station.get(id)
        render view: "show", model: [
                station: station
        ]
    }

    def edit(long id) {
        Station station = Station.get(id)
        render view: "edit", model: [
                station: station
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

    }
}
