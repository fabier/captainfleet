class UrlMappings {

    static mappings = {

        "/zone/deviceLog/$zoneId/$deviceId"(controller: "zone", action: "deviceLog") {
            constraints {
                // apply constraints here
                zoneId(matches: /\d*/)
                deviceId(matches: /\d*/)
            }
        }

        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
                id(matches: /\d*/)
            }
        }

        "/"(controller: "public")
        "404"(view: '/public/404')
        "500"(view: '/public/error')
    }
}
