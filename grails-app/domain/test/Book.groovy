package test

class Book {
    Long id
    String title
    Long pagesNumber
    static belongsTo = [user: User]

    static contraints = {
        title blank: false, nullable: false
        pages_number min: 10
        user nullable: false
    }
}
