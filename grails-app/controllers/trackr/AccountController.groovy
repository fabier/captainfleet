package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class AccountController {

    static defaultAction = "index"

    def springSecurityService

    def index() {
        User user = springSecurityService.currentUser
        render view: "index", model: [user: user]
    }

    def devices() {
        User user = springSecurityService.currentUser
        def devices = UserDevice.findAllByUser(user)*.device
        render view: "devices", model: [devices: devices]
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

    def addDevice() {
        User user = springSecurityService.currentUser
        Device device = Device.findByCode(params.code)
        if (device) {
            UserDevice.create(user, device)
            flash.message = "Vous avez maintenant le boitier '${params.code}' accessible."
            redirect action: "devices"
        } else {
            flash.error = "Impossible de trouver un boitier avec le code '${params.code}'.<br/>Veuillez vérifier votre saisie."
            redirect action: "devices"
        }
    }
}
