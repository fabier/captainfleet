class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "public")
        "404"(view: '/public/404')
        "500"(view: '/public/error')
    }
}
