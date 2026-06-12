package test

class UrlMappings {
    static mappings = {
        "/api/register"(controller: "user", action: "register", method: "POST")
        "/api/users/info"(controller: "user", action: "info", method: "GET")
        "/api/users/update"(controller: "user", action: "update", method: "PUT")

        "/api/books"(controller: "book") {
            action = [GET: "index", POST: "save"]
        }

        "/api/books/$id"(controller: "book") {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
        }

        "/api/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }



        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
