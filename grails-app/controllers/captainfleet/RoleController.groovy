package captainfleet

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class RoleController extends grails.plugin.springsecurity.ui.RoleController {

    def search() {
        def results = Role.createCriteria().list {
            order("authority", "asc")
            maxResults(10)
        }
        [results: results, totalCount: Role.count(), searched: true]
    }
}
