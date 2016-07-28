package captainfleet

import log.AccessLog

class LoggingFilters {

    def filters = {
        allButImage(controller: 'mapMarker', invert: true) {
            before = {
                def remoteAddr = request.getRemoteAddr()
                boolean shouldLogToDatabase = true
                boolean shouldLogToFile = true
                if ("127.0.0.1".equals(remoteAddr) || "0:0:0:0:0:0:0:1".equals(remoteAddr)) {
                    // On n'enregistre pas en base
                    shouldLogToDatabase = false
                    if (controllerName.equals("public") && actionName.equals("index")) {
                        // Acces depuis nagios certainement pour vérifer le bon fonctionnement du service
                        // On ne loggue pas sur fichier
                        shouldLogToFile = false
                    }
                }

                if (shouldLogToFile) {
                    log.info "${request.forwardURI} -> $params (${remoteAddr})"
                }
                if (shouldLogToDatabase) {
                    new AccessLog(
                            ip: remoteAddr,
                            path: request.forwardURI,
                            controller: controllerName,
                            action: actionName,
                            queryString: request.queryString
                    ).save()
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
