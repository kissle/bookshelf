package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.Book

interface CreateBookPort {
    fun create(book: Book): Uni<Book>
}