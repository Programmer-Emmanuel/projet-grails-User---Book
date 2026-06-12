package test

import dto.BookDto
import grails.plugin.springsecurity.annotation.Secured

class BookController {

    static responseFormats = ['json']

    BookService bookService

    @Secured(["ROLE_USER"])
    def save(){
        try{
            // Evite def utilise le typage statique(ça permet à n'importe qui de comprendre facilement ton code)
            def json = request.JSON
            BookDto dto = new BookDto(
                    title: json.title,
                    pages_number: json.pages_number
            )
            // Evite def
            def response = bookService.save(dto)

            respond([
                    status: 1,
                    data: response,
                    message: "Livre créé avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de la creation d’un livre",
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(["ROLE_USER"])
    def index(){
        try{
            def books = bookService.index()

            respond([
                    status: 1,
                    data: books,
                    message: "Livre de l’utilisateur affichée avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de l’affichage de la liste des livres de l’utilisateur",
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(["ROLE_USER"])
    def show (Long id){
        try{
            def book = bookService.show(id)

            respond([
                    status: 1,
                    data: book,
                    message: "Livre affiché avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de l’affichage d’un livre",
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(["ROLE_USER"])
    def update(Long id){
        try{
            // Evite le def
            def json = request.JSON

            BookDto dto = new BookDto(
                    title: json.title,
                    pages_number: json.pages_number
            )

            def response = bookService.update(dto, id)

            respond([
                    status: 1,
                    data: response,
                    message: "Modification du livre effectué avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de la modification du livre d’un utilisateur",
                    erreur: e.message
            ], status: 500)
        }
    }

    @Secured(["ROLE_USER"])
    def delete(Long id){
        try{
            bookService.delete(id)
            respond([
                    status: 1,
                    message: "Livre supprimé avec succès"
            ])
        }
        catch(Exception e){
            respond([
                    status: 0,
                    message: "Erreur lors de la suppression du livre",
                    erreur: e.message
            ], status: 500)
        }
    }
}
