package trackr

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class AccountController {

    static defaultAction = "index"

    SpringSecurityService springSecurityService

    def index() {
        User user = springSecurityService.currentUser
        render view: "index", model: [user: user]
    }

    def preferences() {
        User user = springSecurityService.currentUser
        render view: "preferences", model: [user: user]
    }

    def update(long id) {
        User user = springSecurityService.currentUser
        user.username = params.username
        user.save()

        flash.message = "Modification effectuée"

        redirect action: "index"
    }
}
