package secondbrain.kissle.bookshelf.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface CreateBookShelfUseCase {

    fun create(): Uni<BookShelf>
}