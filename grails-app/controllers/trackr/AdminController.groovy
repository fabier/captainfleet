package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class AdminController {
    static defaultAction = "index"

    def index() {
        redirect controller: "user", action: "userSearch"
    }
}
