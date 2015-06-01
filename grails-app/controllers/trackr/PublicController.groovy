package trackr

import grails.plugin.mail.MailService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured

@Secured(['permitAll'])
class PublicController {

    static defaultAction = "index"

    SpringSecurityService springSecurityService
    def grailsApplication

    MapService mapService
    MailService mailService
    DeviceService deviceService

    def index() {
        User user = springSecurityService.currentUser
        if (user) {
            redirect controller: "common"
        } else {
            def config = SpringSecurityUtils.securityConfig
            def devices = Device.findAllByDeviceState(DeviceState.NORMAL)
            MapOptions mapOptions = mapService.buildFromDevicesUsingRandomFrame(devices)
            render view: "index", model: [
                    devices   : devices,
                    mapOptions: mapOptions,
                    postUrl   : "${request.contextPath}${config.apf.filterProcessesUrl}"
            ]
        }
    }

    def about() {
        render view: "about"
    }

    def contact() {
        render view: "contact"
    }

    def credits() {
        render view: "credits"
    }

    def plans() {
        render view: "plans"
    }

    def '404'() {
        render view: "404"
    }

    def sendMail(ContactCommand contactCommand) {
        if (contactCommand.hasErrors()) {
            flash.warning = "Merci de renseigner votre adresse email, votre sujet et votre message"
            forward action: "contact", params: params
        } else {
            String messageWithBr = contactCommand.message.replaceAll("\\n", "<br/>")
            String message = "<html><head></head><body>${messageWithBr}</body></html>"
            String messageAck = "<html><head></head><body>Bonjour,<br/><br/>" +
                    "Vous venez d'envoyer le message suivant à CaptainFleet.<br/>" +
                    "Nous nous efforçons de répondre à votre demande dans les plus brefs délais.<br/><br/>" +
                    "Cordialement,<br/><br/>" +
                    "L'équipe CaptainFleet.<br/><br/>" +
                    "#######################<br/><br/>" +
                    "Sujet : ${contactCommand.subject}<br/><br/>" +
                    "Message : <br/><br/>" +
                    "${messageWithBr}</body></html>"
            mailService.sendMail {
                async true
                to "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
                subject "[CaptainFleet Contact] ${contactCommand.subject}"
                html message
                from contactCommand.email
            }
            mailService.sendMail {
                async true
                to contactCommand.email
                subject "CaptainFleet - Confirmation d'envoi d'un message"
                html messageAck
                from "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
            }
            flash.message = "Votre message a été envoyé, il sera traité dans les plus brefs délais."
            redirect action: "contact"
        }
    }

    def legal() {
        render view: "legal"
    }

    def cgu() {
        render view: "cgu"
    }

    def cgv() {
        render view: "cgv"
    }

    def onepage() {
        render view: "onepage"
    }

    def sendNameEmailMail(NameEmailCommand nameEmailCommand) {
        if (nameEmailCommand.hasErrors()) {
            flash.warning = "Merci de renseigner votre nom et votre email"
            redirect action: "onepage", params: params, fragment: "contact"
        } else {
            String message = "<html><head></head><body>" +
                    "${nameEmailCommand.name} vous a envoyé un message de demande de contact.<br/><br/>" +
                    "Contactez le sur son adresse mail : ${nameEmailCommand.email}<br/><br/>" +
                    "L'équipe CaptainFleet.<br/><br/>" +
                    "</body></html>"
            String messageAck = "<html><head></head><body>Bonjour,<br/><br/>" +
                    "Vous venez d'envoyer un message de demande de contact à CaptainFleet.<br/>" +
                    "Nous nous efforçons de répondre à votre demande dans les plus brefs délais.<br/><br/>" +
                    "Cordialement,<br/><br/>" +
                    "L'équipe CaptainFleet.<br/><br/>" +
                    "</body></html>"
            mailService.sendMail {
                async true
                to "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
                subject "[CaptainFleet Nom + Email] ${nameEmailCommand.email}"
                html message
                from nameEmailCommand.email
            }
            mailService.sendMail {
                async true
                to nameEmailCommand.email
                subject "CaptainFleet - Confirmation d'envoi d'un message"
                html messageAck
                from "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
            }
            flash.message = "Votre message a été envoyé, il sera traité dans les plus brefs délais."
            redirect action: "onepage"
        }
    }
}

class ContactCommand {

    String email
    String subject
    String message

    static constraints = {
        email(email: true)
        subject(blank: false)
        message(blank: false)
    }
}


class NameEmailCommand {
    String name
    String email

    static constraints = {
        name nullable: false
        email email: true
    }
}