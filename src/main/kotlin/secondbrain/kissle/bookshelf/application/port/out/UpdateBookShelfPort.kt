package secondbrain.kissle.bookshelf.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface UpdateBookShelfPort {
    fun update(bookShelf: BookShelf): Uni<BookShelf>
}