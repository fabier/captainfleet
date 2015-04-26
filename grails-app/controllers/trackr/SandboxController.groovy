package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class SandboxController {

    static defaultAction = "index"

    def index() {

    }
}
