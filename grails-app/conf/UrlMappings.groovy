class UrlMappings {

    static mappings = {

        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
                id(matches: /\d*/)
            }
        }

        "/$controller/$action?/$id1?/$id2?" {
            constraints {
                // apply constraints here
                id1(matches: /\d*/)
                id2(matches: /\d*/)
            }
        }

        "/"(controller: "public")
        "404"(view: '/public/404')
        "500"(view: '/public/error')
    }
}
