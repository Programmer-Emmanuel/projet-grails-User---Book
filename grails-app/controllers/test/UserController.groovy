package test

import dto.UserDto
import grails.plugin.springsecurity.annotation.Secured

class UserController {

    static responseFormats = ['json']

    UserService userService

    def register(){
        try {
            // @TODO/ Evite def
            def json = request.JSON

            UserDto dto = new UserDto(
                    username: json.username,
                    name: json.name,
                    phone: json.phone,
                    password: json.password
            )
            // @TODO/ Evite def
            def response = userService.register(dto)

            respond([
                    status: 1,
                    data: response,
                    message: "Inscription de l’utilisateur réussie"
            ])

        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de l’inscription de l’utilisateur",
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(['ROLE_USER'])
    def info(){
        try{
            // @TODO/ Evite def
            def user = userService.info()

            respond([
                    status: 1,
                    data: user,
                    message: "Informations de l’utilisateur affichées avec succès"
            ])
        }
        catch (Exception e){
            respond([
                    status: 0,
                    message: 'Erreur lors de l’affichage des infos de l’utilisateur',
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(["ROLE_USER"])
    def update(){
        try{
            // @TODO/ Evite def
            def json = request.JSON
            UserDto dto = new UserDto(
                    name : json.name,
                    username: json.username,
                    phone: json.phone
            )
            def user = userService.update(dto)

            respond([
                    status: 1,
                    data: user,
                    message: "Info de l’utilisateur modifié avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de la modification des infos de l’utilisateur",
                    erreur: e.message
            ], status: 500)
        }
    }
}
