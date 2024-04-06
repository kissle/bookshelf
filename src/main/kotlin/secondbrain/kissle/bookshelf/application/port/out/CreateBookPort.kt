package secondbrain.kissle.bookshelf.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.Book

interface CreateBookPort {
    fun create(book: Book): Uni<Book>
}