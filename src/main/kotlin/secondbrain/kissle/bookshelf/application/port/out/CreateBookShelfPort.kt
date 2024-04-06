package secondbrain.kissle.bookshelf.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface CreateBookShelfPort {
    fun create(bookShelf: BookShelf): Uni<BookShelf>
}