package test

import dto.BookDto
import dto.BookResponseDto
import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    UserService userService

    BookResponseDto save(BookDto dto){
        User user = userService.getAuthenticatedUser()

        Book book = new Book(
                title: dto.title,
                pagesNumber: dto.pagesNumber,
                user: user
        )

        book.save(flush: true, failOnError: true)

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pagesNumber: book.pagesNumber,
                user: user.name
        )
    }

    List<BookResponseDto> index(){

        User user = userService.getAuthenticatedUser()

        List<Book> books = Book.findAllByUser(user)

        return books.collect { book ->
            new BookResponseDto(
                    id: book.id,
                    title: book.title,
                    pagesNumber: book.pagesNumber,
                    user: user.name
            )
        }
    }

    Book getBookByIdAndUser(Long id){
        User user = userService.getAuthenticatedUser()
        Book book = Book.findByIdAndUser(id, user)

        if(!book){
            throw RuntimeException("Livre introuvable")
        }

        return book
    }

    BookResponseDto getById(Long id){
        Book book = getBookByIdAndUser(id)

        if(!book){
            throw RuntimeException("Livre introuvable")
        }

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pagesNumber: book.pagesNumber,
                user: book.user.name
        )
    }

    BookResponseDto update(BookDto dto, Long id){
        Book book = getBookByIdAndUser(id)
        
        if(dto.title != null){
            book.title = dto.title
        }
        if(dto.pagesNumber != null){
            book.pagesNumber = dto.pagesNumber
        }
        book.save(flush: true, failOnError: true)

        return new BookResponseDto(
                id: book.id,
                title: book.title,
                pagesNumber: book.pagesNumber,
                user: book.user.name
        )
    }

    void deleteById(Long id){
        Book book = getBookByIdAndUser(id)
        book.delete(flush: true, failOnError: true)
    }
}
