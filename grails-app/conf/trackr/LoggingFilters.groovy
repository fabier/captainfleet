package trackr

class LoggingFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                log.info "controller:$controllerName action:$actionName -> $params"
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
