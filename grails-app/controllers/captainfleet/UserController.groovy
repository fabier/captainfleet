package captainfleet

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class UserController extends grails.plugin.springsecurity.ui.UserController {

    def search() {
        def results = User.createCriteria().list {
            order("username", "asc")
            maxResults(10)
        }
        [results: results, totalCount: User.count(), searched: true]
    }
}
