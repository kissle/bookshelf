package secondbrain.kissle.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.Book

interface CreateBookUseCase {
    fun create(book: Book): Uni<Book>
}