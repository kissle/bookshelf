package secondbrain.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookShelf

interface CreateBookShelfUseCase {

    fun create(): Uni<BookShelf>
}