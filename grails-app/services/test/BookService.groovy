package test

import dto.BookDto
import dto.BookResponseDto
import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    UserService userService

    BookResponseDto save(BookDto dto){
        def user = userService.getUser()

        Book book = new Book(
                title: dto.title,
                pages_number: dto.pages_number,
                user: user
        )

        book.save(flush: true, failOnError: true)

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pages_number: book.pages_number,
                user: user.name
        )
    }

    List<BookResponseDto> index(){

        def user = userService.getUser()

        def books = Book.findAllByUser(user)

        return books.collect { book ->
            new BookResponseDto(
                    id: book.id,
                    title: book.title,
                    pages_number: book.pages_number,
                    user: user.name
            )
        }
    }

    Book getBookUser(Long id){
        def user = userService.getUser()

//        def book = Book.where{
//            user == user
//            id == id
//        }.get()

        def book = Book.findByIdAndUser(id, user)

        if(!book){
            throw RuntimeException("Livre introuvable")
        }

        return book
    }

    BookResponseDto show(Long id){

        def book = getBookUser(id)

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pages_number: book.pages_number,
                user: book.user.name
        )
    }

    BookResponseDto update(BookDto dto, Long id){
        def book = getBookUser(id)

        if(dto.title != null){
            book.title = dto.title
        }
        if(dto.pages_number != null){
            book.pages_number = dto.pages_number
        }
        book.save(flush: true, failOnError: true)

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pages_number: book.pages_number,
                user: book.user.name
        )
    }

    void delete(Long id){
        def book = getBookUser(id)
        book.delete(flush: true, failOnError: true)
    }
}
