import com.vividsolutions.jts.geom.*

// configuration for plugin testing - will not be included in the plugin zip
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
//grails.app.context = "trackr"
//grails.server.port.http = 80

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    warn 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'grails.app.services.grails.plugin.springsecurity.ui.SpringSecurityUiService'

    info "grails.app"
}

environments {
    development {
    }

    test {
    }

    production {
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'trackr.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'trackr.UserRole'
grails.plugin.springsecurity.authority.className = 'trackr.Role'
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/'              : ['permitAll'],
        '/index'         : ['permitAll'],
        '/index.gsp'     : ['permitAll'],
        '/assets/**'     : ['permitAll'],
        '/**/js/**'      : ['permitAll'],
        '/**/css/**'     : ['permitAll'],
        '/**/images/**'  : ['permitAll'],
        '/**/favicon.ico': ['permitAll'],
        '/monitoring'    : ['permitAll'],
        '/monitoring/**' : ['permitAll']
]

grails.plugin.springsecurity.ui.register.postRegisterUrl = '/'
grails.plugin.springsecurity.ui.register.emailFrom = 'noreply@tracklala.com'
grails.plugin.springsecurity.ui.register.emailSubject = 'Tracklala - Valider votre email'
grails.plugin.springsecurity.ui.register.defaultRoleNames = ['ROLE_USER']
grails.plugin.springsecurity.ui.password.validationRegex = '^.*(?=.*[a-zA-Z\\d]).*$' // Au moins quelques caractères
grails.plugin.springsecurity.ui.password.minLength = 4
grails.plugin.springsecurity.ui.password.maxLength = 64
grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugin.springsecurity.logout.postOnly = false

grails {
    plugin {
        springsecurity {

            ui {

                encodePassword = false

                register {
                    emailBody = '''\
Bonjour $user.username,<br/>
<br/>
Vous venez de créer un compte sur <a href="http://www.tracklala.com">Tracklala</a> et nous vous en remercions !<br/>
<br/>
Merci de <strong><a href="$url">cliquer ici</a></strong> pour terminer la procédure d'enregistrement, ou copier coller l'adresse suivante dans votre navigateur :<br/>
$url<br/>
<br/>
Merci de ne pas répondre à ce message automatique.
'''
                    emailFrom = 'Tracklala <noreply@tracklala.com>'
                    emailSubject = 'Tracklala - Création de compte'
                    defaultRoleNames = ['ROLE_USER']
                    postRegisterUrl = null // use defaultTargetUrl if not set
                }

                forgotPassword {
                    emailBody = '''\
Hi $user.username,<br/>
<br/>
Vous, ou quelqu'un se faisant passer pour vous, venez de faire une demande de mise à zéro de votre mot de passe sur <a href="http://www.tracklala.com">Tracklala</a>.<br/>
<br/>
SI vous n'avez pas fait cette demande, alors ignorez ce message et supprimez le, aucun changement de sera appliqué à votre compte.<br/>
<br/>
Si vous êtes bien celui qui a fait la demande, alors <a href="$url">cliquez ici</a> pour remettre à zéro votre mot de passe.
<br/>
Merci de ne pas répondre à ce message automatique.
'''
                    emailFrom = 'Tracklala <noreply@tracklala.com>'
                    emailSubject = 'Tracklala - Réinitialisation du mot de passe'
                    postResetUrl = null // use defaultTargetUrl if not set
                }
            }
        }
    }
}

grails {
    mail {
        host = "smtp.1und1.de"
        port = 587
        username = "noreply@tracklala.com"
        password = "MRr0PGkWS93Kzeb"
        props = ["mail.smtp.auth"                  : "true",
                 "mail.smtp.socketFactory.port"    : "587",
                 "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback": "true"]
    }
}
// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */

// Pour les soucis de pagination avec Bootstrap
grails.plugins.twitterbootstrap.fixtaglib = true

grails.cache.config = {
    defaultCache {
        maxElementsInMemory 10000
        eternal false
        timeToIdleSeconds 86400
        timeToLiveSeconds 86400
        overflowToDisk false
        maxElementsOnDisk 0
        diskPersistent false
        diskExpiryThreadIntervalSeconds 120
        memoryStoreEvictionPolicy 'LRU'
    }

    cache {
        name 'trackr'
        eternal false
        overflowToDisk true
        maxElementsInMemory 10000
        maxElementsOnDisk 10000000
    }

    domain {
        name 'trackr.Role'
        eternal false
        overflowToDisk false
        path "${System.getProperty("user.home")}/.ehcache"
    }

    diskStore {
        path "${System.getProperty("user.home")}/.ehcache"
    }
}

beans {
    cacheManager {
        shared = true
    }
}

/* Added by the Hibernate Spatial Plugin. */
grails.gorm.default.mapping = {
   /* Added by the Hibernate Spatial Plugin. */
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Geometry)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.GeometryCollection)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.LineString)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Point)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Polygon)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.MultiLineString)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.MultiPoint)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.MultiPolygon)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.LinearRing)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Puntal)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Lineal)
   'user-type'(type:org.hibernatespatial.GeometryUserType, class:com.vividsolutions.jts.geom.Polygonal)
}
