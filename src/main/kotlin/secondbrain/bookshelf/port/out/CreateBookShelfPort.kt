package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookShelf

interface CreateBookShelfPort {
    fun create(bookShelf: BookShelf): Uni<BookShelf>
}