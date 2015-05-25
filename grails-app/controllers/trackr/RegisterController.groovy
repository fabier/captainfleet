package trackr

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.plugin.springsecurity.ui.RegistrationCode
import org.springframework.security.access.annotation.Secured

@Secured("permitAll")
class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {

    def index() {
        def copy = [:] + (flash.chainedParams ?: [:])
        copy.remove 'controller'
        copy.remove 'action'
        [command: new RegisterCommand(copy)]
    }

    def register(RegisterCommand command) {

        if (command.hasErrors()) {
            render view: 'index', model: [command: command]
            return
        }

        def User = command.grailsApplication.getDomainClass(SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
        def user = User.findByEmail(command.email)
        if (user) {
            if (user.accountLocked) {
                // On va renvoyer le mail de validation automatiquement
            } else {
                // Le compte existe déjà et il est actif
                flash.error = message(code: 'registerCommand.user.alreadyUnlocked', args: [
                        createLink(controller: "login"),
                        createLink(controller: "register", action: "forgotPassword")
                ])
                flash.chainedParams = params
                redirect action: 'index'
                return
            }
        } else {
            // Création du compte utilisateur
            user = lookupUserClass().newInstance(email: command.email, username: command.username,
                    accountLocked: true, enabled: true)
        }

        String salt = saltSource instanceof NullSaltSource ? null : command.username
        RegistrationCode registrationCode = springSecurityUiService.register(user, command.password, salt)
        if (registrationCode == null || registrationCode.hasErrors()) {
            // null means problem creating the user
            flash.error = message(code: 'spring.security.ui.register.miscError')
            flash.chainedParams = params
            redirect action: 'index'
            return
        }

        // Préparation et envoi du mail
        try {
            String url = generateLink('verifyRegistration', [t: registrationCode.token])
            def conf = SpringSecurityUtils.securityConfig
            def body = conf.ui.register.emailBody
            if (body.contains('$')) {
                body = evaluate(body, [user: user, url: url])
            }
            mailService.sendMail {
                to command.email
                from conf.ui.register.emailFrom
                subject conf.ui.register.emailSubject
                html body.toString()
            }
            render view: 'index', model: [emailSent: true]
        } catch (Exception e) {
            // Le mail n'est pas parti, on affiche une erreur
            flash.error = message(code: 'registerCommand.email.errorDuringSend')
            flash.chainedParams = params
            redirect action: 'index'
        }
    }

    def verifyRegistration() {

        def conf = SpringSecurityUtils.securityConfig
        String defaultTargetUrl = conf.successHandler.defaultTargetUrl

        String token = params.t

        def registrationCode = token ? RegistrationCode.findByToken(token) : null
        if (!registrationCode) {
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: defaultTargetUrl
            return
        }

        def user
        // TODO to ui service
        RegistrationCode.withTransaction { status ->
            String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
            user = lookupUserClass().findWhere((usernameFieldName): registrationCode.username)
            if (!user) {
                return
            }
            user.accountLocked = false
            user.save(flush: true)
            def UserRole = lookupUserRoleClass()
            def Role = lookupRoleClass()
            for (roleName in conf.ui.register.defaultRoleNames) {
                UserRole.create user, Role.findByAuthority(roleName)
            }
            registrationCode.delete()
        }

        if (!user) {
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: defaultTargetUrl
            return
        }

        springSecurityService.reauthenticate user.email

        flash.message = message(code: 'spring.security.ui.register.complete')
        redirect uri: conf.ui.register.postRegisterUrl ?: defaultTargetUrl
    }

}

class RegisterCommand {

    String username
    String email
    String password
    String password2
    boolean acceptsConditions

    def grailsApplication

    static constraints = {
        username blank: false
        email blank: false, email: true
        password blank: false, validator: RegisterController.passwordValidator
        password2 validator: RegisterController.password2Validator
        acceptsConditions notEqual: false
    }
}
