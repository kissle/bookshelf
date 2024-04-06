package secondbrain.kissle.bookshelf.domain

class BookCopy (
    var id: Long? = null,
    var book: Book,
    var mediumType: MediumType
) {
}