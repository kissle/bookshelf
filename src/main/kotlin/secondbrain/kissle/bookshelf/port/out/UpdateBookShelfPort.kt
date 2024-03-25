package secondbrain.kissle.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface UpdateBookShelfPort {
    fun update(bookShelf: BookShelf): Uni<BookShelf>
}