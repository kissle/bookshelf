package secondbrain.kissle.bookshelf.domain

import io.smallrye.mutiny.Uni

class BookShelf(
    var id: Long?,
    val bookCopies: MutableList<BookCopy> = mutableListOf()
) {

    fun addBook(bookCopy: BookCopy): Uni<BookShelf> {
        bookCopies.add(bookCopy)
        return Uni.createFrom().item(this)
    }
}