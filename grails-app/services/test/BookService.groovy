package test

import dto.BookDto
import dto.BookResponseDto
import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    UserService userService

    BookResponseDto save(BookDto dto){
        // @TODO: Evite le def
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

    // Mauvais nommage, getBookByIdAndUser et tu passes le user dépuis le controller
    Book getBookUser(Long id){
        def user = userService.getUser()
            // @TODO: supprime
//        def book = Book.where{
//            user == user
//            id == id
//        }.get()

        // @TODO: Evit def
        def book = Book.findByIdAndUser(id, user)

        if(!book){
            throw RuntimeException("Livre introuvable")
        }

        return book
    }

    // @TODO: Mauvais nommage, getById
    BookResponseDto show(Long id){
        // @TODO: Evit def
        def book = getBookUser(id)

        // @TODO L'exception ??

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pages_number: book.pages_number,
                user: book.user.name
        )
    }

    BookResponseDto update(BookDto dto, Long id){
        // @TODO: Evit def
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

    // @TODO: Mauvais nommage, deleteById
    void delete(Long id){
        // @TODO: Evit def
        def book = getBookUser(id)
        book.delete(flush: true, failOnError: true)
    }
}
