grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
        test   : false, // [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true], // configure settings for the test-app JVM
        run    : false, // [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256], // configure settings for the run-app JVM
        war    : false, // [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256], // configure settings for the run-war JVM
        console: false // [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]// configure settings for the Console UI JVM
]

grails.project.dependency.resolver = 'maven' // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits('global') {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log 'warn' // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenRepo 'http://repository.codehaus.org'
        //mavenRepo 'http://download.java.net/maven/2/'
        //mavenRepo 'http://repository.jboss.com/maven2/'

        // Pour Geotools
        mavenRepo 'http://download.osgeo.org/webdav/geotools/'

        // Pour HibernateSpatial
        mavenRepo 'http://www.hibernatespatial.org/repository/'

        // Pour PostGis
        mavenRepo 'http://www.datanucleus.org/downloads/maven2/'
    }

    dependencies {
        // Driver base de données
        runtime 'org.postgresql:postgresql:42.0.0.jre7'

        // Cache entities
        compile 'net.sf.ehcache:ehcache:2.8.5'

        // Librairies utilitaires
        compile "org.apache.commons:commons-lang3:3.4"

        // Base de données géographique
        compile "com.vividsolutions:jts:1.13"
        compile('net.postgis:postgis-jdbc:2.2.1') {
            exclude 'postgresql:postgresql:9.4.1208.jre7'
        }
        compile('org.hibernatespatial:hibernate-spatial-postgis:1.1.1') {
            exclude 'org.hibernate:hibernate-core:3.6.0.Final'
            excludes 'javassist'
        }
        compile('org.geotools:gt-main:12-RC1')
        compile('org.geotools:gt-referencing:12-RC1')
    }

    plugins {
        // Conteneur Tomcat
        build ':tomcat:8.0.14.1'

        // Resources
        compile ":asset-pipeline:2.1.5"
        compile ":less-asset-pipeline:2.0.8"

        // Bootstrap
        compile ":twitter-bootstrap:3.3.4"
        compile ":jquery:1.11.1"
        compile ":jquery-ui:1.10.4"

        // Others
        compile ":hibernate:3.6.10.19"

        // Gestion utilisateurs
        compile ":spring-security-core:2.0-RC4"
        compile ":spring-security-ui:1.0-RC2"

        // Gestion des emails
        compile ":mail:1.0.7"

        // Statistiques et monitoring
        compile ":grails-melody:1.55.0"

        // Pour Google Analytics
        compile ":google-analytics:2.3.3"

        // Pour afficher des graphiques
        compile ":google-visualization:1.0.2"
    }
}


environments {
    development {
    }

    test {
    }

    production {
    }
}
