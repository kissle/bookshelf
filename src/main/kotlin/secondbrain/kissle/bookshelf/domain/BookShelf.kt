package secondbrain.kissle.bookshelf.domain

import io.smallrye.mutiny.Uni
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

class BookShelf {
    @Id
    @GeneratedValue
    var id: Long? = null

    val books: MutableList<BookCopy> = mutableListOf()

    fun addBook(bookCopy: BookCopy): Uni<BookShelf> {
        books.add(bookCopy)
        return Uni.createFrom().item(this)
    }
}