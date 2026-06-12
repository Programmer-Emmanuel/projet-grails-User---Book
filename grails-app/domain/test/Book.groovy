package test

class Book {
    Long id
    String title
    // @TODO: Gorm fait automatiquement le mapping si tu as pages_number en db ici c'est pagesNumber
    Long pages_number
    static belongsTo = [user: User]

    static contraints = {
        title blank: false, nullable: false
        pages_number min: 10
        user nullable: false
    }
}
