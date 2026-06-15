package test

import dto.BookDto
import dto.BookResponseDto
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class BookController {

    static responseFormats = ['json']

    BookService bookService

    @Secured(["ROLE_USER"])
    def save(){
        try{
            Map<String, Object> json = request.JSON
            BookDto dto = new BookDto(
                    title: json.title,
                    pagesNumber: json.pagesNumber
            )
            BookResponseDto response = bookService.save(dto)

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
            List<BookResponseDto> books = bookService.index()

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
            BookResponseDto book = bookService.getById(id)

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
            Map<String, Object> json = request.JSON

            BookDto dto = new BookDto(
                    title: json.title,
                    pagesNumber: json.pagesNumber
            )

            BookResponseDto response = bookService.update(dto, id)

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
            bookService.deleteById(id)
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
